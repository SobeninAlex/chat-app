package com.example.chat_app.glue.feature.chat

import android.net.Uri
import com.example.chat.FeatureChatRepository
import com.example.domain.Message
import com.example.utils.helper.Constants
import com.example.utils.model.AttachType
import com.example.utils.model.Attachment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import javax.inject.Inject

class FeatureChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
) : FeatureChatRepository {

    override fun getMessages(channelId: String): Query {
        return database.getReference(Constants.REFERENCE_MESSAGES)
            .child(channelId)
            .orderByChild(Constants.REFERENCE_CREATED_AT)
    }

    override fun sendMessage(
        channelId: String,
        message: String?,
        attachmentUrl: String?
    ): Task<Void> {
        val msg = Message(
            id = database.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = auth.currentUser?.uid ?: "",
            message = message,
            senderName = auth.currentUser?.displayName ?: "",
            attachmentUrl = attachmentUrl
        )

        return database.reference
            .child(Constants.REFERENCE_MESSAGES)
            .child(channelId)
            .push()
            .setValue(msg)
    }

    override fun downloadAttachment(attachment: Attachment, channelId: String): Task<Uri>? {
        val id = UUID.randomUUID()
        val reference = when (attachment.type) {
            AttachType.IMAGE -> {
                storage.reference.child("images/$id")
            }

            AttachType.VIDEO -> {
                storage.reference.child("videos/$id")
            }

            AttachType.FILE -> {
                storage.reference.child("files/$id")
            }
        }

        attachment.uri?.let { uri ->
            return reference.putFile(uri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    reference.downloadUrl
                }
        }

        return null
    }
}