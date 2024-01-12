package br.com.aldemir.domain.model

data class MonthlyPaymentDomain(
    var id: Int = 0,
    var id_expense: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var due_date: Int = 0,
    var situation: Boolean = false,
)
