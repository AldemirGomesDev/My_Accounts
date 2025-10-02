package br.com.aldemir.expense.presentation.expensechange.effect

sealed interface ChangeExpenseEffect {
    data object NavigateBack: ChangeExpenseEffect
}