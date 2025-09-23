package br.com.aldemir.expense.model


import kotlinx.datetime.Instant

data class ExpenseView(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var created_at: Instant? = null,
    var due_date: Int = 0,
    var status: Boolean = false,
    var expired: Boolean = false
)
