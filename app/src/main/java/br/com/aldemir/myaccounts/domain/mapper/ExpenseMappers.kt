package br.com.aldemir.myaccounts.domain.mapper

import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.presentation.shared.model.ExpensePerMonthView
import br.com.aldemir.myaccounts.presentation.shared.model.ExpenseView

fun Expense.toView(expired: Boolean) = ExpenseView(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status,
    expired = expired
)

fun ExpensePerMonth.toView(expired: Boolean) = ExpensePerMonthView(
    id_expense = id_expense,
    name = name,
    description = description,
    due_date = due_date,
    year = year,
    month = month,
    value = value,
    situation = situation,
    expired = expired
)

fun ExpenseView.toDatabase() = Expense(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)