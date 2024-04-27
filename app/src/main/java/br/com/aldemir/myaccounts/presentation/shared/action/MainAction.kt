package br.com.aldemir.myaccounts.presentation.shared.action

import br.com.aldemir.common.theme.AppDarkMode

sealed class MainAction {
    object FetchData: MainAction()
    data class UpdateDarkModeState(val appDarkMode: AppDarkMode): MainAction()
}