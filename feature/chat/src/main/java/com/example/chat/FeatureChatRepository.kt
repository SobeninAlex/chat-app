package com.example.chat

import com.google.android.gms.tasks.Task
import com.google.firebase.database.Query

interface FeatureChatRepository {

    fun getMessages(channelId: String) : Query

    fun sendMessage(channelId: String, message: String): Task<Void>

}