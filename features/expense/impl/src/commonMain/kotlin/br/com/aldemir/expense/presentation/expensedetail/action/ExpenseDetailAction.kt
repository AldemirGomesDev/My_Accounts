package br.com.aldemir.expense.presentation.expensedetail.action

sealed class ExpenseDetailAction {
    data class FetchExpenseDetail(val id: Int) : ExpenseDetailAction()
    data class OnUpdateClicked(val id: Int) : ExpenseDetailAction()
    data object UpdateExpenseSituation : ExpenseDetailAction()
    data class ShowDialog(val show: Boolean) : ExpenseDetailAction()
}