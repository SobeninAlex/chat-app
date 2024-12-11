package com.example.utils.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.resourse.MainColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshLayout(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    animationMillis: Long = 0,
    refreshContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }

    val showLoading = isRefreshing || animationStarted
    LaunchedEffect(key1 = showLoading) {
        animationStarted = isRefreshing
        delay(animationMillis)
        animationStarted = false
    }

    val pullRefreshState = rememberPullRefreshState(showLoading, onRefresh)

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        AnimatedVisibility(
            visible = !showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box {
                content()
            }
        }

        AnimatedVisibility(
            visible = showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box {
                refreshContent()
            }
        }

        PullRefreshIndicator(
            refreshing = showLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = MainColor,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}
