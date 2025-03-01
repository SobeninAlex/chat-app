package com.example.utils.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.example.resourse.BlackColor05
import com.example.resourse.BlackColor10
import kotlinx.coroutines.delay

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.clickableOnce(onClick: () -> Unit): Modifier = composed(
    inspectorInfo = {
        name = "clickableOnce"
        value = onClick
    }
) {
    var enableAgain by remember { mutableStateOf(true) }
    LaunchedEffect(enableAgain) {
        if (enableAgain) return@LaunchedEffect
        delay(timeMillis = 500L)
        enableAgain = true
    }
    Modifier.clickable {
        if (enableAgain) {
            enableAgain = false
            onClick()
        }
    }
}


/**
 * на белой secon = BlackColor05
 * на любом другом = WhiteColor50
 */
fun Modifier.shimmerEffect(
    first: Color = Color.Transparent,
    second: Color = BlackColor10, //WhiteColor50
    durationMillis: Int = 1800,
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    var size by remember { mutableStateOf(IntSize.Zero) }

    val translateAnimation  by transition.animateFloat(
        label = "",
        initialValue = -1f * size.width.toFloat(),
        targetValue = 1f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    drawBehind {
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    first,
                    second,
                    second,
                    first
                ),
                startX = translateAnimation,
                endX = translateAnimation + size.width.toFloat()
            )
        )
    }.onGloballyPositioned {
        size = it.size
    }
}
