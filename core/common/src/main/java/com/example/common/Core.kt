package com.example.common

import kotlinx.coroutines.CoroutineScope

object Core {

    private lateinit var coreProvider: CoreProvider

    //val commonUi: CommonUi get() = coreProvider.commonUi

    val resources: AndroidResources get() = coreProvider.resources

    val logger: AndroidLogger get() = coreProvider.logger

    val globalScope: CoroutineScope get() = coreProvider.globalScope

    val errorHandler: DefaultErrorHandler get() = coreProvider.errorHandler

    //val screenCommunication: ScreenCommunication get() = coreProvider.screenCommunication

    fun init(coreProvider: CoreProvider) {
        Core.coreProvider = coreProvider
    }

}