package br.com.aldemir.authentication.presentation

import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.login.R

data class AuthenticationUiModel(
    val state: AuthenticationState = AuthenticationState.IDLE,
    val snackBarState: SnackBarState = SnackBarState.NONE,
    val isBiometricAvailable: Boolean = false,
    val showBiometricPrompt: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val snackBarMessage: Int = R.string.snack_bar_empty
)
