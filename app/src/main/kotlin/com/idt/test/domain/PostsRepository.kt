package com.idt.test.domain

interface PostsRepository {

    suspend fun getPosts(): List<Post>
}