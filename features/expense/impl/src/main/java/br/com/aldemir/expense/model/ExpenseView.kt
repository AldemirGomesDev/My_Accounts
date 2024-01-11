package br.com.aldemir.expense.model


import java.util.*

data class ExpenseView(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var created_at: Date? = null,
    var due_date: Int = 0,
    var status: Boolean = false,
    var expired: Boolean = false
)
