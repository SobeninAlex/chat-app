package com.example.resourse

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

private val defaultFontFamily = FontFamily.SansSerif

// Header 1
val h1_Black32 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Black,
    fontSize = 32.sp,
    letterSpacing = 0.em,
    lineHeight = TextUnit.Unspecified,
)

// Header 2
val h2_Black24 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Black,
    fontSize = 24.sp,
    letterSpacing = 0.em,
    lineHeight = TextUnit.Unspecified
)

// Header 3
val h3_Bold20 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    letterSpacing = 0.em,
    lineHeight = 26.sp
)

// Header 6
val h6_Med20 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    letterSpacing = 0.em,
    lineHeight = 23.sp
)

// Title 1
val t1_Med20 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    letterSpacing = 0.em,
    lineHeight = 22.sp
)

// Title 2
val t2_Bold18 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    letterSpacing = 0.em,
    lineHeight = 22.sp
)

// Title 3
val t3_Bold16 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    letterSpacing = 0.em,
    lineHeight = 22.sp
)

// Title 4
val t4_Med16 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = 0.em,
    lineHeight = 22.sp
)

// Buttons 1
val buttons1_Med16 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = 0.02.em,
    lineHeight = TextUnit.Unspecified
)

// Buttons 2
val buttons2_Med14 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = 0.02.em,
    lineHeight = TextUnit.Unspecified
)

// Body 1
val body1_Reg16 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.02.em,
    lineHeight = TextUnit.Unspecified
)

// Body 1 alt
val body1alt_Light16 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp,
    letterSpacing = 0.em,
    lineHeight = 24.sp
)

// Body 2
val body2_Reg14 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.em,
    lineHeight = TextUnit.Unspecified
)

// Subtitle
val sub_Med14 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = 0.em,
    lineHeight = TextUnit.Unspecified
)

// Caption 1
val cap1_Med12 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.em,
    lineHeight = TextUnit.Unspecified
)

// Caption 2
val cap2_Reg12 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = 0.02.em,
    lineHeight = TextUnit.Unspecified
)

val cap2_Reg14 = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.02.em,
    lineHeight = TextUnit.Unspecified
)

val hyperLink = TextStyle(
    fontFamily = defaultFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.em,
    lineHeight = TextUnit.Unspecified,
    color = HyperLinkColor,
    textDecoration = TextDecoration.Underline
)

val Typography = Typography(
    headlineLarge = h1_Black32,
    headlineMedium = h2_Black24,
    headlineSmall = h3_Bold20,

    titleLarge = t1_Med20,
    titleMedium = t3_Bold16,

    bodyLarge = body1_Reg16,
    bodyMedium = body1alt_Light16,
    bodySmall = body2_Reg14,

    labelLarge = sub_Med14,
    labelMedium = cap1_Med12,
    labelSmall = cap2_Reg12
)