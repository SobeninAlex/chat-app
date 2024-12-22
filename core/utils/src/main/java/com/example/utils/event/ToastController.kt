package com.example.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object ToastController {
    private val _event = Channel<String>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(message: String) {
        _event.send(message)
    }
}