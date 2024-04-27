package br.com.aldemir.common.theme

import br.com.aldemir.common.R

data class DarkModeDropDownState(
    val appDarkMode: AppDarkMode = AppDarkMode.Default,
    val titleRes: Int = R.string.drawer_dark_mode_auto,
    val iconRes: Int = R.drawable.ic_night_sight_auto_24,
)
