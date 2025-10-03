package br.com.aldemir.expense.mapper

import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.expense.model.ExpensePerMonthView
import br.com.aldemir.expense.model.ExpenseView
import br.com.aldemir.expense.model.MonthlyPaymentView

fun MonthlyPaymentDomain.toView() = MonthlyPaymentView(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    due_date = due_date,
    situation = situation,
    expired = checkIfExpired(due_date, month, year)
)

fun ExpensePerMonthDomain.toView() = ExpensePerMonthView(
    id_expense = id_expense,
    name = name,
    description = description,
    due_date = due_date,
    year = year,
    month = month,
    value = value,
    situation = situation,
    expired = checkIfExpired(due_date, month, year)
)

fun ExpensePerMonthDomain.toExpenseView() = ExpenseView(
    id = id_expense,
    name = name,
    description = description,
    due_date = due_date,
    status = situation,
    expired = checkIfExpired(due_date, month, year)
)

fun ExpenseView.toDomain() = ExpenseDomain(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)

private fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
    return (year == DateUtils.getYearString() && month == DateUtils.getMonthString() && DateUtils.getCurrentDay() > dueDay)
}