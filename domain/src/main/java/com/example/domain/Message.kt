package com.example.domain

import com.example.utils.model.Attachment

data class Message(
    val id: String = "",
    val senderId: String = "",
    val message: String? = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String = "",
    val senderImage: String? = null,
    val attachment: Attachment? = null,
)