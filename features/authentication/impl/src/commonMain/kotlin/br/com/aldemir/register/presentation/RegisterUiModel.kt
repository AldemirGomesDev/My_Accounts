package br.com.aldemir.register.presentation

import br.com.aldemir.common.component.SnackBarState
import myaccounts.features.authentication.impl.generated.resources.Res
import myaccounts.features.authentication.impl.generated.resources.register_error
import org.jetbrains.compose.resources.StringResource

data class RegisterUiModel(
    val isLoading: Boolean = false,
    val name: String = "",
    val userName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isError: Boolean = false,
    val snackBarUiModel: SnackBarUiModel = SnackBarUiModel(),
)

data class SnackBarUiModel(
    val snackBarState: SnackBarState = SnackBarState.NONE,
    val message: StringResource = Res.string.register_error,
)
