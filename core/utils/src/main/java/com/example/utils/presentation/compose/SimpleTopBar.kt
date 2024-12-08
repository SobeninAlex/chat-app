package com.example.utils.presentation.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resourse.MainColor
import com.example.resourse.t2_Bold18
import com.example.resourse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopBar(
    modifier: Modifier = Modifier,
    title: String,
    goBack: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    navigationIconContentColor: Color = MainColor,
    titleContentColor: Color = MaterialTheme.colorScheme.onBackground,
    actionIconContentColor: Color = MainColor,
    actions: (@Composable () -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = t2_Bold18,
            )
        },
        navigationIcon = {
            goBack?.let { back ->
                NavigationTopBarIcon(onClick = back)
            }
        },
        actions = {
            actions?.invoke()
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            navigationIconContentColor = navigationIconContentColor,
            titleContentColor = titleContentColor,
            actionIconContentColor = actionIconContentColor
        ),
        modifier = modifier.shadow(elevation = 1.dp)
    )
}

@Composable
fun NavigationTopBarIcon(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back_base),
            contentDescription = "Назад",
        )
    }
}

@Preview
@Composable
private fun SimpleTopBarPreview() {
    SimpleTopBar(title = "Title")
}