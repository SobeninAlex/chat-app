package com.example.chat.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.chat.presentation.components.ChatBubble
import com.example.chat.presentation.components.SenderMessagesBox
import com.example.navigation.LocalNavController
import com.example.utils.presentation.compose.AttachmentPickerBottomSheet
import com.example.utils.presentation.compose.LoadingBox
import com.example.utils.presentation.compose.SimpleTopBar
import com.example.utils.presentation.noRippleClickable
import com.example.utils.presentation.setupSystemBarStyleDefault
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(channelId: String, channelName: String) {
    val context = LocalContext.current
    context.setupSystemBarStyleDefault(
        statusBarColor = Color.Transparent
    )

    val viewModel = hiltViewModel<ChatViewModel, ChatViewModel.Factory> { factory ->
        factory.create(channelId = channelId, channelName = channelName)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChatContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@Composable
private fun ChatContent(
    uiState: ChatUiState,
    event: (ChatEvent) -> Unit
) {
    val navController = LocalNavController.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SimpleTopBar(
                title = uiState.channelName,
                goBack = { navController.popBackStack() },
            )
        }
    ) { paddingValues ->
        LoadingBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            isLoading = uiState.loading
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
            ) {
                val lazyListState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(uiState.messages) {
                    if (uiState.messages.isNotEmpty()) {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(uiState.messages.size - 1)
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = lazyListState
                ) {
                    items(
                        items = uiState.messages,
                    ) { msg ->
                        ChatBubble(
                            modifier = Modifier.animateItem(),
                            message = msg,
                            currentUserId = uiState.currentUserId
                        )
                    }
                }

                SenderMessagesBox(
                    onSendMessage = { event(ChatEvent.SendMessage(it)) },
                    onClickAttach = { showBottomSheet = true },
                    sendAttachmentProcess = uiState.sendAttachmentProcess
                )
            }
        }
    }

    if (showBottomSheet) {
        AttachmentPickerBottomSheet(
            onPick = { attachments ->
                if (attachments.isNotEmpty()) {
                    event(ChatEvent.SendAttachments(attachments))
                }
            },
            onDismissRequest = { showBottomSheet = false }
        )
    }
}
