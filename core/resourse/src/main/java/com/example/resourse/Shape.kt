package com.example.resourse

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val roundedCornerShape4 = RoundedCornerShape(4.dp)
val roundedCornerShape8 = RoundedCornerShape(8.dp)
val roundedCornerShape12 = RoundedCornerShape(12.dp)
val roundedCornerShape16 = RoundedCornerShape(16.dp)
val roundedCornerShape20 = RoundedCornerShape(20.dp)

val currentUserBubbleShape = RoundedCornerShape(
    topStart = 12.dp,
    topEnd = 12.dp,
    bottomStart = 12.dp
)
val anotherUserBubbleShape = RoundedCornerShape(
    topEnd = 12.dp,
    bottomStart = 12.dp,
    bottomEnd = 12.dp
)

val roundedBottomCornerShape16 = RoundedCornerShape(
    bottomEnd = 16.dp,
    bottomStart = 16.dp
)

val Shapes = Shapes(
    extraSmall = roundedCornerShape4,
    small = roundedCornerShape8,
    medium = roundedCornerShape12,
    large = roundedCornerShape16,
    extraLarge = roundedCornerShape20,
)
