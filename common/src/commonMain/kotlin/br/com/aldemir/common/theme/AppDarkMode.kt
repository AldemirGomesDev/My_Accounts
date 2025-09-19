package br.com.aldemir.common.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.drawer_dark_mode_auto
import myaccounts.common.generated.resources.drawer_dark_mode_disabled
import myaccounts.common.generated.resources.drawer_dark_mode_enabled
import org.jetbrains.compose.resources.StringResource

enum class AppDarkMode(
    val titleRes: StringResource,
    val imageVector: ImageVector = Icons.Filled.Star,
) {
    Light(
        titleRes = Res.string.drawer_dark_mode_disabled,
        imageVector = Icons.Filled.Warning
    ),
    Dark(
        titleRes = Res.string.drawer_dark_mode_enabled,
        imageVector = Icons.Filled.Build
    ),
    Default(
        titleRes = Res.string.drawer_dark_mode_auto,
        imageVector = Icons.Filled.Star
    )
}