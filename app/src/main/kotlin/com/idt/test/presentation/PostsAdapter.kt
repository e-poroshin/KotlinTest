package com.idt.test.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.idt.test.databinding.ItemPostBinding

class PostsAdapter(private val onPostClick: (PostUiModel) -> Unit) :
    ListAdapter<PostUiModel, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostUiModel) {
            binding.apply {
                titleText.text = post.title
                root.setOnClickListener { onPostClick(post) }
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<PostUiModel>() {
        override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel) =
            oldItem == newItem
    }
} 