package com.example.chat_app.glue.feature.home

import com.example.home.FeatureHomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    @Singleton
    fun bindFeatureHomeRepository(impl: FeatureHomeRepositoryImpl): FeatureHomeRepository

}