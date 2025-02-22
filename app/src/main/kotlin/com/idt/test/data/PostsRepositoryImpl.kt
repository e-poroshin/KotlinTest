package com.idt.test.data

import com.idt.test.domain.Post
import com.idt.test.domain.PostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApi
): PostsRepository {

    override suspend fun getPosts(): List<Post> = withContext(Dispatchers.IO) {
        api.getPosts().map { it.toPost() }
    }
} 