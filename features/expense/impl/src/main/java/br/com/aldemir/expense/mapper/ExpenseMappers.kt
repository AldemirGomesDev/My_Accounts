package br.com.aldemir.expense.mapper

import br.com.aldemir.data.database.model.ExpenseDTO
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import br.com.aldemir.data.database.model.MonthlyPaymentDomain
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

fun MonthlyPaymentView.toDatabase() = ExpenseMonthlyDTO(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    situation = situation,
)

fun ExpensePerMonthDTO.toView(expired: Boolean) = ExpensePerMonthView(
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

fun ExpensePerMonthDTO.toExpenseView(expired: Boolean) = ExpenseView(
    id = id_expense,
    name = name,
    description = description,
    due_date = due_date,
    status = situation,
    expired = expired
)

fun ExpenseView.toDatabase() = ExpenseDTO(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)