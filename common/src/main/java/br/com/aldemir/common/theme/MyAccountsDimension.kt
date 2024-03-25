package br.com.aldemir.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MyAccountsDimension {
    val sizing0: Dp
        @Composable
        get() = 0.dp
    val sizing4: Dp
        @Composable
        get() = 4.dp
    val sizing8: Dp
        @Composable
        get() = 8.dp
    val sizing12: Dp
        @Composable
        get() = 12.dp
    val sizing16: Dp
        @Composable
        get() = 16.dp
    val sizing20: Dp
        @Composable
        get() = 20.dp

    val sizing24: Dp
        @Composable
        get() = 24.dp

    val sizing28: Dp
        @Composable
        get() = 28.dp

    val sizing32: Dp
        @Composable
        get() = 32.dp

    val sizing48: Dp
        @Composable
        get() = 48.dp

    val sizing52: Dp
        @Composable
        get() = 52.dp

    val padding2: Dp
        @Composable
        get() = 2.dp

    val padding4: Dp
        @Composable
        get() = 4.dp

    val padding6: Dp
        @Composable
        get() = 6.dp

    val padding8: Dp
        @Composable
        get() = 8.dp

    val padding10: Dp
        @Composable
        get() = 10.dp

    val padding12: Dp
        @Composable
        get() = 12.dp


    val padding16: Dp
        @Composable
        get() = 16.dp

    val padding20: Dp
        @Composable
        get() = 20.dp

    val padding24: Dp
        @Composable
        get() = 24.dp

    val padding28: Dp
        @Composable
        get() = 28.dp

    val padding32: Dp
        @Composable
        get() = 32.dp

    val padding48: Dp
        @Composable
        get() = 48.dp

    val padding52: Dp
        @Composable
        get() = 52.dp

}

internal val LocalDimensions = staticCompositionLocalOf { MyAccountsDimension() }