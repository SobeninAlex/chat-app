package com.example.chat.presentation

import com.example.utils.model.Attachment

sealed interface ChatEvent {

    data class SendMessage(val msg: String) : ChatEvent

    data class SendAttachments(val attachments: List<Attachment>) : ChatEvent

}