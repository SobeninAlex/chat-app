package com.example.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.resourse.MainColor
import com.example.resourse.WhiteColor
import com.example.utils.presentation.compose.ActionIconButton
import com.example.utils.presentation.compose.SimpleTextFieldOutlined

@Composable
fun SenderMessagesBox(
    onSendMessage: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var msg by remember { mutableStateOf("") }

        SimpleTextFieldOutlined(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = msg,
            maxLines = 4,
            placeholder = "your message",
            onValueChange = { msg = it }
        )

        Spacer(modifier = Modifier.width(8.dp))

        ActionIconButton(
            modifier = Modifier.padding(top = 6.dp),
            icon = Icons.Outlined.Send,
            tint = WhiteColor,
            enabled = msg.isNotBlank(),
            containerColor = MainColor,
            onClick = {
                onSendMessage(msg)
                msg = ""
            }
        )
    }
}