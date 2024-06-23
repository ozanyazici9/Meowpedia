package com.ozanyazici.meowpedia.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ozanyazici.meowpedia.R

private val fontFamilySurfer = FontFamily(
    listOf(
        Font(
            resId = R.font.original_surfer
        )
    )
)

val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamilySurfer,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilySurfer,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.15).sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.4).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.25).sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = (0.5).sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilySurfer,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = (0.6).sp,
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 17.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.1).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamilySurfer,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.1).sp
    )
)
