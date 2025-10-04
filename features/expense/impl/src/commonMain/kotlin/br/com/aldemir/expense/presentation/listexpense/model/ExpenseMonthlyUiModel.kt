package br.com.aldemir.expense.presentation.listexpense.model

data class ExpenseMonthlyUiModel(
    var id: Int = 0,
    var id_expense: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var situation: Boolean = false
)
