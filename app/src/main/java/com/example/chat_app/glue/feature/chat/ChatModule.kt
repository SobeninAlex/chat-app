package com.example.chat_app.glue.feature.chat

import com.example.chat.FeatureChatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ChatModule {

    @Binds
    @Singleton
    fun bindFeatureChatRepository(impl: FeatureChatRepositoryImpl): FeatureChatRepository

}