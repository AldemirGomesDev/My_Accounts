package br.com.aldemir.myaccounts.presentation.shared.action

sealed class MainAction {
    object FetchData: MainAction()
    data class UpdateDarkModeState(val isDarkMode: Boolean): MainAction()
}