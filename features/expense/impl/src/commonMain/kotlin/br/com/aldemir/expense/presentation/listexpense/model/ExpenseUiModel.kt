package br.com.aldemir.expense.presentation.listexpense.model

import br.com.aldemir.expense.model.ExpenseView

data class ExpenseUiModel(
    val expenses: List<ExpenseView> = emptyList(),
    val monthExpenses: List<ExpenseMonthlyUiModel> = emptyList(),
    val showDialog: Boolean = false,
    val totalValue: Double = 0.0,
    val paidOut: Double = 0.0,
    val pending: Double = 0.0,
    val percentage: Float = 0F
)
