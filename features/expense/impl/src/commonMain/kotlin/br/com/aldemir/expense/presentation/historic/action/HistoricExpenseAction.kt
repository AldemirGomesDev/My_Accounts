package br.com.aldemir.expense.presentation.historic.action

sealed class HistoricExpenseAction {
    data class OnSearchClicked(val month: String, val year: String) : HistoricExpenseAction()
    object FetchData : HistoricExpenseAction()
    data class UpdateMonthSelected(val month: String) : HistoricExpenseAction()
}