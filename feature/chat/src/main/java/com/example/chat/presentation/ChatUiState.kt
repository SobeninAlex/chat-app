package com.example.chat.presentation

import com.example.domain.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

data class ChatUiState(
    val loading: Boolean = false,
    val channelName: String = "",
    val messages: List<Message> = emptyList(),
    val sendAttachmentProcess: Boolean = false
) {

    val currentUserId: String
        get() = Firebase.auth.currentUser?.uid ?: ""

}