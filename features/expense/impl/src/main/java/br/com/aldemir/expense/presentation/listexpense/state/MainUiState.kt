package br.com.aldemir.expense.presentation.listexpense.state

import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.DarkModeDropDownState

data class MainUiState(
    val appDarkMode: AppDarkMode = AppDarkMode.Default,
    val appDarkModeSelected: DarkModeDropDownState = DarkModeDropDownState(),
    val listItems: List<DarkModeDropDownState> = listOf(),
)
