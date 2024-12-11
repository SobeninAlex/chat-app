package com.example.utils.presentation.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.resourse.MainColor
import com.example.resourse.MainColor10
import com.example.resourse.MainColor20
import com.example.resourse.WhiteColor
import com.example.resourse.roundedCornerShape12
import com.example.resourse.roundedCornerShape16
import com.example.resourse.t3_Bold16

@Composable
fun SubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = roundedCornerShape12,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MainColor,
        contentColor = WhiteColor
    ),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        content()
    }
}

@Composable
fun ApplyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = roundedCornerShape12,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MainColor,
        contentColor = WhiteColor
    ),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    interactionSource: MutableInteractionSource? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            style = t3_Bold16
        )
    }
}

@Composable
fun ActionIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color = WhiteColor,
    containerColor: Color = MainColor,
    onClick: () -> Unit,
    size: Dp = 48.dp,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier.size(size),
        onClick = onClick,
        enabled = enabled,
        shape = roundedCornerShape16,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        )
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = icon,
            tint = tint,
            contentDescription = null,
        )
    }
}