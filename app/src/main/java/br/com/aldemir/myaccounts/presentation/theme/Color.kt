package br.com.aldemir.myaccounts.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFF442C2E)
val Purple500 = Color(0xFFFBB8AC)
val Purple700 = Color(0xFFA0522D)
val Teal200 = Color(0xFF03DAC5)

val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFD3D3D3)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF32CD32)
val MediumPriorityColor = Color(0xFFFFA500)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = MediumGray

val Colors.splashScreenBackground: Color
    @Composable
    get() = if (isLight) Purple700 else Color.Black

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.addAccountLabelColor: Color
    @Composable
    get() = if (isLight) Purple200 else MediumGray

val Colors.addAccountBorderColor: Color
    @Composable
    get() = if (isLight) Purple200 else Color.White

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple200 else Purple700

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackGroundColor: Color
    @Composable
    get() = if (isLight) Purple200 else Color.Black

val Colors.paidTextColor: Color
    @Composable
    get() = if (isLight) LowPriorityColor else LowPriorityColor