package com.example.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


object ExitController {
    private val _event = Channel<Unit>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent() {
        _event.send(Unit)
    }
}