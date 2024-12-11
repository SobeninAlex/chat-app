package com.example.chat.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.utils.presentation.setupSystemBarStyleDefault

@Composable
fun ChatScreen(channelId: String) {
    val context = LocalContext.current
    context.setupSystemBarStyleDefault(
        statusBarColor = Color.Transparent
    )
}