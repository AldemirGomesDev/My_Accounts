package br.com.aldemir.register.presentation

import br.com.aldemir.common.component.SnackBarState
import org.jetbrains.compose.resources.StringResource

class RegisterUiMapper {
    fun mapToUiModel(
        errorMessage: StringResource,
        snackBarState: SnackBarState,
        registerUiModel: RegisterUiModel = RegisterUiModel()
    ): RegisterUiModel {
        return when (snackBarState) {
            SnackBarState.NONE -> registerUiModel
            SnackBarState.ERROR, SnackBarState.ALERT -> registerUiModel.copy(
                isError = true,
                isLoading = false,
                snackBarUiModel = SnackBarUiModel(
                    snackBarState = snackBarState,
                    message = errorMessage
                )
            )
            SnackBarState.SUCCESS -> registerUiModel.copy(
                snackBarUiModel = SnackBarUiModel(
                    snackBarState = snackBarState,
                    message = errorMessage
                )
            )
        }
    }
}