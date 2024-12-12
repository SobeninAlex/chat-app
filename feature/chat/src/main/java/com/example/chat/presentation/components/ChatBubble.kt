package com.example.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.domain.Message
import com.example.resourse.MainColor
import com.example.resourse.WhiteColor
import com.example.resourse.anotherUserBubbleShape
import com.example.resourse.body2_Reg14
import com.example.resourse.currentUserBubbleShape

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: Message,
    currentUserId: String
) {
    val isCurrentUser = message.senderId == currentUserId
    val bubbleColor = if (isCurrentUser) MainColor else MaterialTheme.colorScheme.primaryContainer
    val textColor = if (isCurrentUser) WhiteColor else MaterialTheme.colorScheme.onBackground
    val alignment = if (isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd
    val shape = if (isCurrentUser) currentUserBubbleShape else anotherUserBubbleShape

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Box(
            modifier = modifier
                .widthIn(max = 250.dp)
                .clip(shape)
                .background(bubbleColor)
                .padding(8.dp)
        ) {
            Text(
                text = message.message,
                color = textColor,
                style = body2_Reg14
            )
        }
    }
}