package com.example.home.presentation.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.Channel
import com.example.resourse.body1_Reg16
import com.example.utils.presentation.compose.ClickableRoundedColumn

fun LazyListScope.channelsBlock(
    channels: List<Channel>,
    onChannelClick: (Channel) -> Unit
) = items(
    items = channels,
    key = { it.id }
) { channel ->
    ChannelItem(
        modifier = Modifier.animateItem(),
        channel = channel,
        onClick = { onChannelClick(channel) }
    )
}

@Composable
private fun ChannelItem(
    modifier: Modifier = Modifier,
    channel: Channel,
    onClick: () -> Unit
) {
    ClickableRoundedColumn(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            text = channel.name,
            color = MaterialTheme.colorScheme.onBackground,
            style = body1_Reg16
        )
    }
}

@Preview
@Composable
private fun ChannelItemPreview() {
    ChannelItem(
        channel = Channel.FAKE,
        onClick = {}
    )
}