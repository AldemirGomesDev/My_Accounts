package br.com.aldemir.expense.presentation.expensechange.model

import br.com.aldemir.common.util.emptyString

data class ChangeExpenseUiModel(
    var idMonthlyPayment: Int = 0,
    var idExpense: Int = 0,
    var year: String = emptyString(),
    var month: String = emptyString(),
    var value: Double = 0.0,
    var situation: Boolean = false,
    val loading: Boolean = false,
)
