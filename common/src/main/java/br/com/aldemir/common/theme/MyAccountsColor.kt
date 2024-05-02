package br.com.aldemir.common.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MyAccountsColor(
    val primary: Color,
    val onPrimary: Color,
    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val outline: Color,
    val backgroundGreen: Color,
)

internal val LocalColors = staticCompositionLocalOf { MyAccountsColor(
    primary = Color.Unspecified,
    onPrimary = Color.Unspecified,
    error = Color.Unspecified,
    onError = Color.Unspecified,
    success = Color.Unspecified,
    onSuccess = Color.Unspecified,
    warning = Color.Unspecified,
    onWarning = Color.Unspecified,
    background = Color.Unspecified,
    onBackground = Color.Unspecified,
    surface = Color.Unspecified,
    onSurface = Color.Unspecified,
    outline = Color.Unspecified,
    backgroundGreen = Color.Unspecified
) }

