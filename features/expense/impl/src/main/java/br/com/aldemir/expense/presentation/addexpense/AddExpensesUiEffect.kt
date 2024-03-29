package br.com.aldemir.expense.presentation.addexpense

import br.com.aldemir.common.component.SnackBarState

sealed class AddExpensesUiEffect {
    data class ShowError(
        val snackBarState: SnackBarState = SnackBarState.ERROR
    ) : AddExpensesUiEffect()
    data class ShowSuccess(
        val snackBarState: SnackBarState = SnackBarState.SUCCESS
    ) : AddExpensesUiEffect()
    object Idle: AddExpensesUiEffect()
}
