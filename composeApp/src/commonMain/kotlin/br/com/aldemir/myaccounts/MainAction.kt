package br.com.aldemir.myaccounts

import br.com.aldemir.common.theme.AppDarkMode

sealed class MainAction {
    data object FetchData: MainAction()
    data class Logout(val userName: String): MainAction()
    data class UpdateDarkModeState(val appDarkMode: AppDarkMode): MainAction()
}