package com.example.common

import android.content.Context
import kotlinx.coroutines.CoroutineScope

class CoreProvider(
    private val appContext: Context,
    //val commonUi: CommonUi = AndroidCommonUi(appContext),
    val resources: AndroidResources = AndroidResources(appContext),
    //val screenCommunication: ScreenCommunication = DefaultScreenCommunication(),
    val globalScope: CoroutineScope = createDefaultGlobalScope(),
    val logger: AndroidLogger = AndroidLogger(),
    val errorHandler: DefaultErrorHandler = DefaultErrorHandler(
        logger, resources, globalScope
    )
)