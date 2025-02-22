package com.idt.test.data

import com.idt.test.domain.Post
import kotlinx.serialization.Serializable


@Serializable
data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

fun PostDto.toPost(): Post = Post(
    id = id,
    title = title,
    body = body,
    userId = userId
)