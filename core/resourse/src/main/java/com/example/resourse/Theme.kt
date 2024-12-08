package com.example.resourse

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MainColor,
    secondary = AccentColor,
    background = BackgroundColorDark,
    primaryContainer = BackgroundCardColorDark,
    onBackground = OnBackgroundColorDark,
    outline = DividerColorDark,
    tertiaryContainer = TextInputColorDark,
)

private val LightColorScheme = lightColorScheme(
    primary = MainColor,
    secondary = AccentColor,
    background = BackgroundColorLight,
    primaryContainer = BackgroundCardColorLight,
    onBackground = OnBackgroundColorLight,
    outline = DividerColorLight,
    tertiaryContainer = TextInputColorLight,
)

@Composable
fun ChatappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}