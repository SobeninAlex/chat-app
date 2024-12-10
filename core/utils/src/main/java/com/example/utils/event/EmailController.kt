package com.example.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object EmailController {
    private val _event = Channel<String>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(email: String) {
        _event.send(email)
    }
}