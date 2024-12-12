package com.example.chat.presentation

import com.example.chat.FeatureChatRepository
import com.example.domain.Message
import com.example.utils.presentation.BaseViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = ChatViewModel.Factory::class)
class ChatViewModel @AssistedInject constructor(
    private val repository: FeatureChatRepository,
    @Assisted private val channelId: String,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(channelId: String) : ChatViewModel
    }

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    fun onEvent(event: ChatEvent) = when (event) {
        is ChatEvent.SendMessage -> { sendMessage(message = event.msg) }
    }

    private fun loadContent() {
        _uiState.update { it.copy(loading = true) }
        getChatContent()
    }

    private fun sendMessage(message: String) {
        repository.sendMessage(channelId, message)
            .addOnSuccessListener {
                getChatContent()
            }
    }

    private fun getChatContent() {
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
    }

}