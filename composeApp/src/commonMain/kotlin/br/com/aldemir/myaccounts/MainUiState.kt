package br.com.aldemir.myaccounts

import br.com.aldemir.common.theme.AppDarkMode
data class MainUiState(
    val appDarkMode: AppDarkMode = AppDarkMode.Default,
    val listItems: List<AppDarkMode> = listOf(),
)