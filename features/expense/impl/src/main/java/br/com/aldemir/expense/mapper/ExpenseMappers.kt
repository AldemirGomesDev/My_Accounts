package br.com.aldemir.expense.mapper

import br.com.aldemir.data.database.model.ExpenseDTO
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import br.com.aldemir.data.database.model.MonthlyPaymentDTO
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.expense.model.ExpensePerMonthView
import br.com.aldemir.expense.model.ExpenseView
import br.com.aldemir.expense.model.MonthlyPaymentView

fun MonthlyPaymentDomain.toView(expired: Boolean) = MonthlyPaymentView(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    due_date = due_date,
    situation = situation,
    expired = expired
)

fun MonthlyPaymentView.toDomain() = ExpenseMonthlyDomain(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    situation = situation,
)

fun ExpensePerMonthDomain.toView(expired: Boolean) = ExpensePerMonthView(
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

fun ExpensePerMonthDomain.toExpenseView(expired: Boolean) = ExpenseView(
    id = id_expense,
    name = name,
    description = description,
    due_date = due_date,
    status = situation,
    expired = expired
)

fun ExpenseView.toDomain() = ExpenseDomain(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)