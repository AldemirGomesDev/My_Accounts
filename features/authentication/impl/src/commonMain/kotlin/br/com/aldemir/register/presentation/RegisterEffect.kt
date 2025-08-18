package br.com.aldemir.register.presentation

import br.com.aldemir.common.component.SnackBarState
import org.jetbrains.compose.resources.StringResource

sealed class RegisterEffect {
    data class ShowSnackBar(
        val message: StringResource,
        val snackBarState: SnackBarState = SnackBarState.NONE
    ) : RegisterEffect()

    data object NavigateBack : RegisterEffect()

    data object NavigateToLogin : RegisterEffect()

    data object NavigateToHome : RegisterEffect()

    data object ShowLoading : RegisterEffect()

    data object HideLoading : RegisterEffect()
}