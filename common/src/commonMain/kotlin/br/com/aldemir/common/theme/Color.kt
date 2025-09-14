package br.com.aldemir.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFF226551)
val Purple500 = Color(0xFF75b9a0)
val Purple700 = Color(0xFF3e9278)
val Teal200 = Color(0xFF03DAC5)

val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFD3D3D3)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val GreenDark = Color(0xFF133329)
val GreenMedium = Color(0xFF3e9278)
val GreenLight = Color(0xFF226551)
val Green200 = Color(0xFFb9d9c1)

val LowPriorityColor = Color(0xFF32CD32)
val MediumPriorityColor = Color(0xFFFFA500)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = MediumGray

val primaryLight =Color(0xFF75b9a0)
val primaryLightVariant =Color(0xFFf2ffff)
val lightSecondary = Color(0xFFefd8bf)
val lightSecondaryVariant = Color(0xFFefd8bf)

val successLight = Color(0xFF32CD32)
val successDark = Color(0xFF32CD32)

val warningLight = Color(0xFFFFA500)
val warningDark = Color(0xFFFFA500)

val Black = Color(0xFF000000)
val White2= Color(0xFFFFFFFF)
val RedErrorDark = Color(0xFFB00020)
val RedErrorLight = Color(0xFFEF5350)

val primaryDark =Color(0xFF226551)
val primaryDarkVariant =Color(0xFF00001a)
val darkSecondary = Color(0xFF402810)
val darkSecondaryVariant = Color(0xFF200000)

val ColorScheme.taskItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Purple700 else Purple200

val ColorScheme.taskItemBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else Color.White

val ColorScheme.dividerColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) MediumGray else LightGray

val ColorScheme.addAccountLabelColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Purple700 else Purple200

val ColorScheme.addAccountBorderColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Purple700 else Purple200
