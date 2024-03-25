package br.com.aldemir.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MyAccountsTypography {
    val h1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale32,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
        )
    val h2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale24,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
        )
    val h3: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val titleNormal: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Normal,
            lineHeight = 30.sp,
        )

    val titleMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Medium,
            lineHeight = 30.sp,
        )

    val titleBold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val subTitleNormal: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale18,
            fontWeight = FontWeight.Normal,
            lineHeight = 30.sp,
        )

    val subTitleMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale18,
            fontWeight = FontWeight.Medium,
            lineHeight = 30.sp,
        )

    val subTitleBold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale18,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val paragraph01: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale12,
            fontWeight = FontWeight.Normal,
        )

    val paragraph02: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale14,
            fontWeight = FontWeight.Normal,
        )

    val paragraph03: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = FontSize.scale16,
            fontWeight = FontWeight.Normal,
        )

}

internal val LocalTypography = staticCompositionLocalOf { MyAccountsTypography() }
