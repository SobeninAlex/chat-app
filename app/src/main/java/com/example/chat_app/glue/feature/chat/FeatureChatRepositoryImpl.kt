package com.example.chat_app.glue.feature.chat

import com.example.chat.FeatureChatRepository
import com.example.domain.Message
import com.example.utils.helper.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.util.UUID
import javax.inject.Inject

class FeatureChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) : FeatureChatRepository {

    override fun getMessages(channelId: String) : Query {
        return database.getReference(Constants.REFERENCE_MESSAGES)
            .child(channelId)
            .orderByChild(Constants.REFERENCE_CREATED_AT)
    }

    override fun sendMessage(channelId: String, message: String): Task<Void> {
        val msg = Message(
            id = database.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = auth.currentUser?.uid ?: "",
            message = message,
            senderName = auth.currentUser?.displayName ?: "",
        )

        return database.reference
            .child(Constants.REFERENCE_MESSAGES)
            .child(channelId)
            .push()
            .setValue(msg)
    }
}