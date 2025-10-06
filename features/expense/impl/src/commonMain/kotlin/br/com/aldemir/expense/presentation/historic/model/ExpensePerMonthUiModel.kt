package br.com.aldemir.expense.presentation.historic.model

data class ExpensePerMonthUiModel(
    var idExpense: Int = 0,
    var name: String = "",
    var description: String = "",
    var dueDate: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var situation: Boolean = false,
    var expired: Boolean = false
)
