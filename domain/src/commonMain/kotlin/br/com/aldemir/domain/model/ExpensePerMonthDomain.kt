package br.com.aldemir.domain.model

data class ExpensePerMonthDomain(
    var id_expense: Int = 0,
    var name: String = "",
    var description: String = "",
    var due_date: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var situation: Boolean = false
)
