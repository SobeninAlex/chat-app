package com.example.home.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton

@Composable
fun CallButton(
    isVideoCall: Boolean,
    onClick: (ZegoSendCallInvitationButton) -> Unit,
) {
    AndroidView(
        factory = {
            val button = ZegoSendCallInvitationButton(it)
            button.setIsVideoCall(isVideoCall)
            button.resourceID = "zego_data"
            button
        },
        modifier = Modifier.size(25.dp),
        update = { zegoCallButton ->
            zegoCallButton.setOnClickListener { _ ->  onClick(zegoCallButton) }
        }
    )
}