package br.com.aldemir.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MyAccountsTypography {
    val h1: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale32,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
        )
    val h2: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale24,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
        )
    val h3: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val h4: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale20,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val titleNormal: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Normal,
            lineHeight = 30.sp,
        )

    val titleMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Medium,
            lineHeight = 30.sp,
        )

    val titleBold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale22,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val subTitleNormal: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale18,
            fontWeight = FontWeight.Normal,
            lineHeight = 30.sp,
        )

    val subTitleMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale18,
            fontWeight = FontWeight.Medium,
            lineHeight = 30.sp,
        )

    val subTitleBold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale18,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
        )

    val paragraph01: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale12,
            fontWeight = FontWeight.Normal,
        )

    val paragraph02Normal: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale14,
            fontWeight = FontWeight.Normal,
        )

    val paragraph02Medium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale14,
            fontWeight = FontWeight.Medium,
        )

    val paragraph02Bold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale14,
            fontWeight = FontWeight.Bold,
        )

    val paragraph03Normal: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale16,
            fontWeight = FontWeight.Normal,
        )

    val paragraph03Medium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale16,
            fontWeight = FontWeight.Medium,
        )

    val paragraph03Bold: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = MyAccountsFont,
            fontSize = FontSize.scale16,
            fontWeight = FontWeight.Bold,
        )

}

internal val LocalTypography = staticCompositionLocalOf { MyAccountsTypography() }
