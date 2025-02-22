package com.idt.test.domain

import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {

    suspend operator fun invoke(): Result<List<Post>> = runCatching {
        postsRepository.getPosts()
    }
}