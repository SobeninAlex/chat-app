package com.example.chat_app.glue

import android.content.Context
import com.example.common.AndroidLogger
import com.example.common.AndroidResources
import com.example.common.Core
import com.example.common.CoreProvider
import com.example.common.DefaultErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext context: Context
    ) : CoreProvider {
        return CoreProvider(context)
    }

    @Provides
    fun provideCoroutineScope() : CoroutineScope {
        return Core.globalScope
    }

    @Provides
    fun provideAndroidLogger() : AndroidLogger {
        return Core.logger
    }

    @Provides
    fun provideAndroidResources() : AndroidResources {
        return Core.resources
    }

    @Provides
    fun provideErrorHandler() : DefaultErrorHandler {
        return Core.errorHandler
    }

}