package com.example.chat.presentation

sealed interface ChatEvent {

    data class SendMessage(val msg: String) : ChatEvent

}