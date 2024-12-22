package com.example.chat.presentation

import android.widget.Toast
import com.example.chat.FeatureChatRepository
import com.example.domain.Message
import com.example.utils.event.SnackbarAction
import com.example.utils.model.Attachment
import com.example.utils.presentation.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel @AssistedInject constructor(
    private val repository: FeatureChatRepository,
    @Assisted("channelId") private val channelId: String,
    @Assisted("channelName") private val channelName: String,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("channelId") channelId: String,
            @Assisted("channelName") channelName: String
        ): ChatViewModel
    }

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(channelName = channelName) }
        loadContent()
    }

    fun onEvent(event: ChatEvent) = when (event) {
        is ChatEvent.SendMessage -> {
            sendMessage(message = event.msg)
        }

        is ChatEvent.SendAttachments -> {
            downloadAttachments(attachments = event.attachments)
        }
    }

    private fun loadContent() {
        _uiState.update { it.copy(loading = true) }
        getChatContent(channelId)
    }

    private fun sendMessage(message: String) {
        repository.sendMessage(channelId = channelId, message = message)
    }

    private fun downloadAttachments(attachments: List<Attachment>) {
        _uiState.update { it.copy(sendAttachmentProcess = true) }
        attachments.forEach { item ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.downloadAttachment(item, channelId)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _uiState.update { it.copy(sendAttachmentProcess = false) }
                            val downloadUri = task.result.toString()
                            repository.sendMessage(
                                channelId = channelId,
                                attachment = Attachment(
                                    name = item.name,
                                    type = item.type,
                                    isUploading = true,
                                    remoteUrl = downloadUri
                                ),
                            ).addOnSuccessListener {
                                getChatContent(channelId)
                            }
                        } else {
                            _uiState.update { it.copy(sendAttachmentProcess = false) }
                            showSnackbar(message = "downloader error")
                        }
                    }
            }
        }
    }

    private fun getChatContent(channelId: String) {
        repository.getMessages(channelId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = snapshot.children.map { data ->
                        data.getValue(Message::class.java) ?: Message()
                    }
                    _uiState.update { state ->
                        state.copy(
                            loading = false,
                            messages = messages,
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _uiState.update { it.copy(loading = false) }
                    showSnackbar(error.message)
                }
            })
        subscribeForNotification(channelId)
        registerUserIdToChannel(channelId)
    }

    private fun registerUserIdToChannel(channelId: String) {
        repository.registerUserIdToChannel(channelId)
    }

    private fun subscribeForNotification(chanelId: String) {
        repository.subscribeForNotification(chanelId)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast(message = "Subscribe to topic: group_$chanelId")
                } else {
                    showToast(message = "Failed to subscribe to topic: group_$chanelId")
                }
            }
    }

}