package com.example.domain

import java.util.UUID

data class Channel(
    val id: String = "",
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        val FAKE = Channel(
            id = UUID.randomUUID().toString(),
            name = "channel_1"
        )
    }
}