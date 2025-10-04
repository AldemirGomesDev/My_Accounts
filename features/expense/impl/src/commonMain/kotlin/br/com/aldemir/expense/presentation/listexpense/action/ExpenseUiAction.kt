package br.com.aldemir.expense.presentation.listexpense.action

import br.com.aldemir.expense.model.ExpenseView

sealed class ExpenseUiAction {
    data object LoadData : ExpenseUiAction()
    data class DeleteExpense(val expenseView: ExpenseView) : ExpenseUiAction()
    data object UpdateExpenseMonthly : ExpenseUiAction()
    data class ShowDialog(val show: Boolean) : ExpenseUiAction()
}