package br.com.aldemir.expense.model

data class ExpensePerMonthView(
    var id_expense: Int = 0,
    var name: String = "",
    var description: String = "",
    var due_date: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double,
    var situation: Boolean = false,
    var expired: Boolean = false
)
