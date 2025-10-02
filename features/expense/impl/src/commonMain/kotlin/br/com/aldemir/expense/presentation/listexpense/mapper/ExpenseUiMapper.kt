package br.com.aldemir.expense.presentation.listexpense.mapper

import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.expense.presentation.listexpense.model.ExpenseMonthlyUiModel

fun ExpenseMonthlyDomain.toUiModel(): ExpenseMonthlyUiModel {
    return ExpenseMonthlyUiModel(
        id = this.id,
        id_expense = this.id_expense,
        year = this.year,
        month = this.month,
        value = this.value,
        situation = this.situation
    )
}