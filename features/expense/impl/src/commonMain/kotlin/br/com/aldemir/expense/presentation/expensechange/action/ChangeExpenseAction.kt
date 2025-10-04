package br.com.aldemir.expense.presentation.expensechange.action

sealed class ChangeExpenseAction {
    data class LoadExpense(val id: Int) : ChangeExpenseAction()
    data class OnValueChange(val value: String) : ChangeExpenseAction()
    data class UpdateMonthlyExpense(val id: Int) : ChangeExpenseAction()
}