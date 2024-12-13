package com.example.utils.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resourse.MainColor
import com.example.resourse.roundedBottomCornerShape16

@Composable
fun SearchBlockContent(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(roundedBottomCornerShape16)
            .background(MainColor)
            .padding(16.dp)
    ) {
        SimpleTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(text)
            },
            placeholder = "search channel"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SearchBlockContentPreview() {
    SearchBlockContent(
        onValueChange = {}
    )
}