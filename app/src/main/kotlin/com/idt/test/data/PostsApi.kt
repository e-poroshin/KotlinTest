package com.idt.test.data

import retrofit2.http.GET

interface PostsApi {

    @GET(Constants.POSTS_ENDPOINT)
    suspend fun getPosts(): List<PostDto>
} 