package com.example.chat_app.glue.feature.chat

import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chat.FeatureChatRepository
import com.example.chat_app.R
import com.example.domain.Message
import com.example.utils.helper.Constants
import com.example.utils.model.AttachType
import com.example.utils.model.Attachment
import com.google.android.gms.tasks.Task
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

class FeatureChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val messaging: FirebaseMessaging,
    @ApplicationContext private val context: Context
) : FeatureChatRepository {

    override fun getMessages(channelId: String): Query {
        return database.getReference(Constants.REFERENCE_MESSAGES)
            .child(channelId)
            .orderByChild(Constants.REFERENCE_CREATED_AT)
    }

    override fun sendMessage(
        channelId: String,
        message: String?,
        attachment: Attachment?,
    ): Task<Void> {
        val msg = Message(
            id = database.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = auth.currentUser?.uid ?: "",
            message = message,
            senderName = auth.currentUser?.displayName ?: "",
            attachment = attachment
        )

        return database.reference
            .child(Constants.REFERENCE_MESSAGES)
            .child(channelId)
            .push()
            .setValue(msg)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    postNotificationToUsers(
                        channelId = channelId,
                        senderName = msg.senderName,
                        messageContent = message.orEmpty(),
                    )
                }
            }
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

    override fun subscribeForNotification(chanelId: String): Task<Void> {
        return messaging.subscribeToTopic("group_$chanelId")
    }

    override fun registerUserIdToChannel(channelId: String) {
        val ref =
            database.reference.child("channels").child(channelId).child("users")
        ref.child(auth.currentUser?.uid ?: "")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        ref.child(auth.currentUser?.uid ?: "").setValue(auth.currentUser?.email)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun postNotificationToUsers(
        channelId: String,
        senderName: String,
        messageContent: String,
    ) {
        val jsonBody = JSONObject().apply {
            put("message", JSONObject().apply {
                put("topic", "group_$channelId")
                put("notification", JSONObject().apply {
                    put("title", "New message in $channelId")
                    put("body", "$senderName: $messageContent")
                })
            })
        }

        val requestBody = jsonBody.toString()

        val request = object : StringRequest(
            Method.POST,
            Constants.FCM_URL,
            Response.Listener {
                Log.d("FeatureChatRepository", "send notification group_$channelId is success")
            },
            Response.ErrorListener {
                Log.d("FeatureChatRepository", "send notification group_$channelId is failure")
            }
        ) {
            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val accessToken = getAccessToken()
                headers["Authorization"] = "Bearer $accessToken"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    private fun getAccessToken(): String {
        val inputStream = context.resources.openRawResource(R.raw.attach_files_compose_key)
        val googleCredentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf(Constants.FIREBASE_MESSAGING))
        return googleCredentials.refreshAccessToken().tokenValue
    }
}