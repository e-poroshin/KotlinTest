package com.idt.test.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idt.test.domain.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val state: StateFlow<PostsUiState> = _state

    private var allPosts: List<PostUiModel> = emptyList()

    init {
        loadPosts()
    }

    fun filterPosts(query: String) {
        if (query.isEmpty()) {
            _state.value = PostsUiState.Success(allPosts)
            return
        }
        val filteredPosts = allPosts.filter {
            it.title.contains(query, ignoreCase = true)
        }
        _state.value = if (filteredPosts.isEmpty()) {
            PostsUiState.Empty
        } else {
            PostsUiState.Success(filteredPosts)
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            getPostsUseCase()
                .onSuccess { posts ->
                    allPosts = posts.map { it.toUiModel() }
                    _state.value = if (posts.isEmpty()) {
                        PostsUiState.Empty
                    } else {
                        PostsUiState.Success(allPosts)
                    }
                }
                .onFailure {
                    _state.value = PostsUiState.Error(it)
                }
        }
    }
} 