package com.example.chat_app.glue.feature.auth

import com.example.auth.FeatureAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @Singleton
    fun bindFeatureAuthRepository(impl: FeatureAuthRepositoryImpl): FeatureAuthRepository

}