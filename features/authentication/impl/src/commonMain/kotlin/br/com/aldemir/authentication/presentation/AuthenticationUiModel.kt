package br.com.aldemir.authentication.presentation

import br.com.aldemir.common.component.SnackBarState
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.snack_bar_empty
import org.jetbrains.compose.resources.StringResource

data class AuthenticationUiModel(
    val state: AuthenticationState = AuthenticationState.IDLE,
    val snackBarState: SnackBarState = SnackBarState.NONE,
    val isBiometricAvailable: Boolean = false,
    val showBiometricPrompt: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val snackBarMessage: StringResource = Res.string.snack_bar_empty
)
