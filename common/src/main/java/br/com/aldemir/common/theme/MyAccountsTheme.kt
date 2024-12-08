package br.com.aldemir.common.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object MyAccountsTheme {
    val colors: MyAccountsColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: MyAccountsTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: MyAccountsDimension
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val radius: MyAccountsRadius
        @Composable
        @ReadOnlyComposable
        get() = LocalRadius.current

    val elevation: MyAccountsElevation
        @Composable
        @ReadOnlyComposable
        get() = LocalElevation.current

    @Composable
    fun MyAccountsTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        typography: MyAccountsTypography = MyAccountsTheme.typography,
        dimensions: MyAccountsDimension = MyAccountsTheme.dimensions,
        elevation: MyAccountsElevation = MyAccountsTheme.elevation,
        radius: MyAccountsRadius = MyAccountsTheme.radius,
        content: @Composable () -> Unit,
    ) {

        val myAccountsColor = MyAccountsColor(
            primary = if (darkTheme) primaryLight else primaryDark,
            onPrimary = if (darkTheme) primaryLight else primaryDark,
            second = if (darkTheme) LightGray else White,
            error = if (darkTheme) RedErrorLight else RedErrorDark,
            onError = if (darkTheme) RedErrorLight else RedErrorDark,
            success = if (darkTheme) primaryLight else primaryDark,
            onSuccess = if (darkTheme) primaryLight else primaryDark,
            warning = if (darkTheme) primaryLight else primaryDark,
            onWarning = if (darkTheme) primaryLight else primaryDark,
            background = if (darkTheme) Black else White,
            onBackground = if (darkTheme) primaryDark else Green200,
            surface = if (darkTheme) primaryLight else primaryDark,
            onSurface = if (darkTheme) primaryLight else primaryDark,
            outline = if (darkTheme) primaryLight else primaryDark,
            backgroundGreen = if (darkTheme) GreenDark else GreenLight
        )

        CompositionLocalProvider(
            LocalColors provides myAccountsColor,
            LocalDimensions provides dimensions,
            LocalTypography provides typography,
            LocalElevation provides elevation,
            LocalRadius provides radius,
            LocalIndication provides rememberRipple(),
        ) {
            content()
        }
    }
}