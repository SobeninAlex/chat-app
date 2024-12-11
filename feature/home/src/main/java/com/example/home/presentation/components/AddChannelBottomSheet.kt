package com.example.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.resourse.GreenColor
import com.example.resourse.MainColor20
import com.example.resourse.t3_Bold16
import com.example.utils.presentation.compose.ActionIconButton
import com.example.utils.presentation.compose.SimpleBottomSheet
import com.example.utils.presentation.compose.TextFieldOutlined

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChannelBottomSheet(
    onDismissRequest: () -> Unit,
    onAddChannel: (String) -> Unit
) {
    SimpleBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = null,
        paddingValues = PaddingValues(16.dp)
    ) {
        var channelName by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Add new channel",
                style = t3_Bold16,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextFieldOutlined(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    value = channelName,
                    singleLine = true,
                    placeholder = "name new channel",
                    onValueChange = { channelName = it }
                )

                Spacer(modifier = Modifier.width(8.dp))

                ActionIconButton(
                    modifier = Modifier.padding(top = 6.dp),
                    icon = Icons.Outlined.Check,
                    tint = GreenColor,
                    containerColor = MainColor20,
                    onClick = {
                        onAddChannel(channelName)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}