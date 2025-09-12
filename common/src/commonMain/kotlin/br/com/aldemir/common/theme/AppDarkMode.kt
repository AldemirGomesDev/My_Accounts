package br.com.aldemir.common.theme

import br.com.aldemir.common.R
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.drawer_dark_mode_auto
import myaccounts.common.generated.resources.drawer_dark_mode_disabled
import myaccounts.common.generated.resources.drawer_dark_mode_enabled
import org.jetbrains.compose.resources.StringResource

enum class AppDarkMode(
    val titleRes: StringResource,
    val iconRes: Int = R.drawable.ic_night_sight_auto_24,
) {
    Light(
        titleRes = Res.string.drawer_dark_mode_disabled,
        iconRes = R.drawable.ic_light_mode_24
    ),
    Dark(
        titleRes = Res.string.drawer_dark_mode_enabled,
        iconRes = R.drawable.ic_dark_mode_24
    ),
    Default(
        titleRes = Res.string.drawer_dark_mode_auto,
        iconRes = R.drawable.ic_night_sight_auto_24
    )
}