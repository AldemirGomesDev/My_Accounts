package br.com.aldemir.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MyAccountsRadius {
    val spacing0: Dp
        @Composable
        get() = 0.dp
    val spacing4: Dp
        @Composable
        get() = 4.dp
    val spacing8: Dp
        @Composable
        get() = 8.dp
    val spacing12: Dp
        @Composable
        get() = 12.dp
    val spacing16: Dp
        @Composable
        get() = 16.dp
    val spacing20: Dp
        @Composable
        get() = 20.dp
}

internal val LocalRadius = staticCompositionLocalOf { MyAccountsRadius() }