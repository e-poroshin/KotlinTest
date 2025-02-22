package com.idt.test.presentation

import android.os.Parcelable
import com.idt.test.domain.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostUiModel(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
): Parcelable

fun Post.toUiModel(): PostUiModel {
    return PostUiModel(
        id = id,
        title = title,
        body = body,
        userId = userId
    )
}