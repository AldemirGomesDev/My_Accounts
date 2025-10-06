package br.com.aldemir.expense.presentation.historic.mapper

import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.expense.presentation.historic.model.ExpensePerMonthUiModel

fun List<ExpensePerMonthDomain>.toListView():  List<ExpensePerMonthUiModel> {
    return this.map {
        ExpensePerMonthUiModel(
            idExpense = it.id_expense,
            name = it.name,
            description = it.description,
            dueDate = it.due_date,
            year = it.year,
            month = it.month,
            value = it.value,
            situation = it.situation,
            expired = checkIfExpired(it.due_date, it.month, it.year)
        )
    }
}

private fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
    return (year == DateUtils.getYearString() && month == DateUtils.getMonthString() && DateUtils.getCurrentDay() > dueDay)
}