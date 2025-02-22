package com.idt.test.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.idt.test.R
import com.idt.test.databinding.FragmentPostsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsListFragment : Fragment() {

    private var _binding: FragmentPostsListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: PostsViewModel by viewModels()
    private val adapter = PostsAdapter { post ->
        findNavController().navigate(
            PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(post)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            postsRecyclerView.adapter = adapter
            searchEdit.doAfterTextChanged { text ->
                viewModel.filterPosts(text?.toString().orEmpty())
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collectLatest { state ->
                    handleState(state)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: PostsUiState) {
        when (state) {
            is PostsUiState.Loading -> {
                showLoadingIndicator(true)
            }

            is PostsUiState.Empty -> {
                showLoadingIndicator(false)
                setListVisibility(false)
                showSnackBar(getString(R.string.no_items_found))
            }

            is PostsUiState.Success -> {
                handleData(state.posts)
            }

            is PostsUiState.Error -> {
                showLoadingIndicator(false)
                setListVisibility(false)
                state.error.message?.let { errorMessage ->
                    showSnackBar(errorMessage)
                }
            }
        }
    }

    private fun handleData(posts: List<PostUiModel>) {
        showLoadingIndicator(false)
        setListVisibility(true)
        adapter.submitList(posts)
    }

    private fun showLoadingIndicator(isVisible: Boolean) {
        binding.loadingIndicator.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setListVisibility(isVisible: Boolean) {
        binding.postsRecyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
} 