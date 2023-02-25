package br.com.aldemir.myaccounts.domain.mapper

import br.com.aldemir.myaccounts.data.model.Expense
import br.com.aldemir.myaccounts.data.model.ExpensePerMonth
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain
import br.com.aldemir.myaccounts.presentation.shared.model.ExpensePerMonthView
import br.com.aldemir.myaccounts.presentation.shared.model.ExpenseView
import br.com.aldemir.myaccounts.presentation.shared.model.MonthlyPaymentView

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

fun ExpensePerMonth.toExpenseView(expired: Boolean) = ExpenseView(
    id = id_expense,
    name = name,
    description = description,
    due_date = due_date,
    status = situation,
    expired = expired
)

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

fun MonthlyPaymentView.toDatabase() = MonthlyPayment(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    situation = situation,
)

fun ExpenseView.toDatabase() = Expense(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)