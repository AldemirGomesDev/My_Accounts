package br.com.aldemir.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MyAccountsElevation {
    val spacing0: Dp
        @Composable
        get() = 0.dp
    val spacing1: Dp
        @Composable
        get() = 4.dp
    val spacing2: Dp
        @Composable
        get() = 8.dp
    val spacing3: Dp
        @Composable
        get() = 12.dp
    val spacing4: Dp
        @Composable
        get() = 16.dp
    val spacing5: Dp
        @Composable
        get() = 20.dp
}

internal val LocalElevation = staticCompositionLocalOf { MyAccountsElevation() }