package com.example.utils.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.resourse.AccentColor
import kotlinx.coroutines.delay

@Composable
fun LoadingBox(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    animationMillis: Long = 0, //минимальное время проигрывания анимации загрузки перед показом контента
    loadingIndicator: @Composable () -> Unit = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DotsLoadingIndicator()
        }
    },
    content: @Composable BoxScope.() -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }

    // запускаем анимацию загрузки
    val showLoading = isLoading || animationStarted
    LaunchedEffect(key1 = showLoading) {
        animationStarted = isLoading
        delay(animationMillis)
        animationStarted = false
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (showLoading) {
            loadingIndicator()
        }
        AnimatedVisibility(
            visible = !showLoading,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Box(
                modifier = modifier
            ) {
                content()
            }
        }
    }
}

@Composable
fun BoxScope.CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    visibility: Boolean,
    color: Color = AccentColor,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    backgroundColor: Color = Color.Transparent,
    strokeCap: StrokeCap = StrokeCap.Square,
) {

    AnimatedVisibility(
        modifier = modifier,
        visible = visibility,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = strokeWidth,
            backgroundColor = backgroundColor,
            strokeCap = strokeCap
        )
    }
}

@Composable
fun DotsLoadingIndicator(
    dotsColor: Color = Color.Gray,
    dotsSize: Dp = 8.dp,
    spaceBetween: Dp = 4.dp,
    travelDistance: Dp = 12.dp
) {
    val dots = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )
    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    keyframes {
                        durationMillis = 1200
                        0f at 0 using LinearOutSlowInEasing
                        1f at 300 using LinearOutSlowInEasing
                        0f at 600 using LinearOutSlowInEasing
                        0f at 1200 using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    Row(
        horizontalArrangement = Arrangement.spacedBy(spaceBetween),
    ) {
        dots.map { it.value }.forEach { value ->
            Box(
                modifier = Modifier
                    .size(dotsSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(color = dotsColor, shape = CircleShape)
            )
        }
    }
}
