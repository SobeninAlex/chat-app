package com.example.chat

import android.net.Uri
import com.example.utils.model.Attachment
import com.google.android.gms.tasks.Task
import com.google.firebase.database.Query

interface FeatureChatRepository {

    fun getMessages(channelId: String): Query

    fun sendMessage(
        channelId: String,
        message: String? = null,
        attachment: Attachment? = null,
    ): Task<Void>

    fun downloadAttachment(
        attachment: Attachment,
        channelId: String
    ): Task<Uri>?

}