package com.idt.test.di

import com.idt.test.data.PostsRepositoryImpl
import com.idt.test.domain.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPostsRepository(repository: PostsRepositoryImpl): PostsRepository
}