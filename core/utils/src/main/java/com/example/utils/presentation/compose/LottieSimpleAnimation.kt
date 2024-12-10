package com.example.utils.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieSimpleAnimation(
    modifier: Modifier = Modifier,
    animation: Int,
    speed: Float = 2f,
    play: Boolean = true,
    repeat: Boolean = false
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animation))
    LottieAnimation(
        composition = composition,
        modifier = modifier,
        speed = speed,
        isPlaying = play,
        iterations = if (repeat) LottieConstants.IterateForever else 1
    )
}