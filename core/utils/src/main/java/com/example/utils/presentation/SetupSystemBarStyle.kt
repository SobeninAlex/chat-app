package com.example.utils.presentation

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.resourse.BackgroundCardColorDark
import com.example.resourse.BackgroundCardColorLight
import com.example.resourse.BackgroundColorDark
import com.example.resourse.BackgroundColorLight
import com.example.resourse.BlackColor

/**
 * примеры функций для смены цвета системного статус бара, и системной панели навигации
 */

/**
 * enableEdgeToEdge
 */
fun Context.setupSystemBarStyle(
    statusBarColor: Color,
    navigationBarColor: Color,
    isLightIconsStatusBar: Boolean,
) = with(this as ComponentActivity) {
    val statusBarStyle = if (!isLightIconsStatusBar) {
        SystemBarStyle.dark(statusBarColor.toArgb())
    } else {
        SystemBarStyle.light(statusBarColor.toArgb(), BlackColor.toArgb())
    }

    val navigationBarStyle = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> {
            SystemBarStyle.dark(navigationBarColor.toArgb())
        }
        else -> {
            SystemBarStyle.light(navigationBarColor.toArgb(), BlackColor.toArgb())
        }

    }

    enableEdgeToEdge(
        statusBarStyle = statusBarStyle,
        navigationBarStyle = navigationBarStyle
    )
}

fun Context.setupSystemBarStyleDefault(
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null
) = with(this as ComponentActivity) {
    val statusBarStyle: SystemBarStyle
    val navigationBarStyle: SystemBarStyle

    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> {
            statusBarStyle = SystemBarStyle.dark(statusBarColor?.toArgb() ?: BackgroundCardColorDark.toArgb())
            navigationBarStyle = SystemBarStyle.dark(navigationBarColor?.toArgb() ?: BackgroundCardColorDark.toArgb())
        }

        else -> {
            statusBarStyle =
                SystemBarStyle.light(statusBarColor?.toArgb() ?: BackgroundCardColorLight.toArgb(), BlackColor.toArgb())
            navigationBarStyle =
                SystemBarStyle.light(navigationBarColor?.toArgb() ?: BackgroundCardColorLight.toArgb(), BlackColor.toArgb())
        }
    }

    enableEdgeToEdge(
        statusBarStyle = statusBarStyle,
        navigationBarStyle = navigationBarStyle
    )
}

fun Context.setupSystemBarStyleDark(
    statusBarColor: Color = BackgroundCardColorDark,
    navigationBarColor: Color = BackgroundColorDark
) = with(this as ComponentActivity) {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.dark(statusBarColor.toArgb()),
        navigationBarStyle = SystemBarStyle.dark(navigationBarColor.toArgb()),
    )
}

fun Context.setupSystemBarStyleLight(
    statusBarColor: Color = BackgroundCardColorLight,
    navigationBarColor: Color = BackgroundColorLight
) = with(this as ComponentActivity) {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.light(statusBarColor.toArgb(), BlackColor.toArgb()),
        navigationBarStyle = SystemBarStyle.light(navigationBarColor.toArgb(), BlackColor.toArgb())
    )
}

//==================================================================================================

/**
 * window
 */
@Composable
fun SystemBarsColorChanger(
    statusSystemBarColor: Color?,
    navSystemBarColor: Color?,
    isLightIcons: Boolean
) {
    val window = (LocalContext.current as Activity).window
    val view = LocalView.current

    SideEffect {
        changeSystemBarsColor(
            window = window,
            view = view,
            statusSystemBarColor = statusSystemBarColor,
            navSystemBarColor = navSystemBarColor,
            isLightIcons = isLightIcons
        )
    }
}

fun changeSystemBarsColor(
    window: Window,
    view: View,
    statusSystemBarColor: Color?,
    navSystemBarColor: Color?,
    isLightIcons: Boolean
) {
    statusSystemBarColor?.let {
        window.statusBarColor = it.toArgb()
    }

    navSystemBarColor?.let {
        window.navigationBarColor = it.toArgb()
    }

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isLightIcons
}
