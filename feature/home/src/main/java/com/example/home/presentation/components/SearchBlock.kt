package com.example.home.presentation.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import com.example.utils.presentation.compose.SearchBlockContent

fun LazyListScope.searchBlock(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) = item {
    SearchBlockContent(
        modifier = modifier,
        onValueChange = onValueChange
    )
}