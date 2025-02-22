package com.idt.test.presentation

sealed class PostsUiState {
    data object Empty : PostsUiState()
    data object Loading : PostsUiState()
    data class Error(val error: Throwable) : PostsUiState()
    data class Success(val posts: List<PostUiModel>) : PostsUiState()
}
