package com.example.chat.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.Message
import com.example.resourse.AccentColor
import com.example.resourse.BlackColor
import com.example.resourse.GrayColor
import com.example.resourse.GrayColor10
import com.example.resourse.GrayColor20
import com.example.resourse.GrayColor50
import com.example.resourse.MainColor
import com.example.resourse.MainColor10
import com.example.resourse.MainColor20
import com.example.resourse.R
import com.example.resourse.WhiteColor
import com.example.resourse.anotherUserBubbleShape
import com.example.resourse.body2_Reg14
import com.example.resourse.currentUserBubbleShape
import com.example.resourse.roundedCornerShape12
import com.example.resourse.roundedCornerShape4
import com.example.resourse.roundedCornerShape8
import com.example.utils.model.AttachType
import com.example.utils.model.Attachment

private var counter = 0

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: Message,
    currentUserId: String
) {
    val isCurrentUser = message.senderId == currentUserId
    val bubbleColor = if (isCurrentUser) MainColor else MaterialTheme.colorScheme.primaryContainer
    val textColor = if (isCurrentUser) WhiteColor else MaterialTheme.colorScheme.onBackground
    val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd
    val shape = if (isCurrentUser) currentUserBubbleShape else anotherUserBubbleShape

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        if (!isCurrentUser && counter == 0) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null,
                tint = GrayColor,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart)
            )
            counter++
        } else if (isCurrentUser) {
            counter = 0
        }

        Row {
            Spacer(modifier = Modifier.width(44.dp))

            Box(
                modifier = Modifier
                    .widthIn(max = 250.dp)
                    .clip(shape)
                    .background(bubbleColor)
                    .padding(8.dp)
            ) {
                message.message?.let {
                    Text(
                        text = it,
                        color = textColor,
                        style = body2_Reg14
                    )
                }

                message.attachment?.let {
                    AttachmentItem(it)
                }
            }
        }
    }
}

@Composable
private fun AttachmentItem(attachment: Attachment) {
    when (attachment.type) {
        AttachType.IMAGE -> {
            AsyncImage(
                model = attachment.remoteUrl,
                placeholder = painterResource(R.drawable.preview_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(roundedCornerShape12)
            )
        }

        AttachType.VIDEO -> {
            Image(
                painter = painterResource(R.drawable.preview_video),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(roundedCornerShape12)
            )
        }

        AttachType.FILE -> {
            Image(
                painter = painterResource(attachment.icon),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(roundedCornerShape12)
            )
        }
    }
}