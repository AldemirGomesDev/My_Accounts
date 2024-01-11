package br.com.aldemir.myaccounts.domain.mapper

import br.com.aldemir.data.database.model.ExpenseDTO
import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import br.com.aldemir.expense.model.ExpenseView

fun ExpenseDTO.toView(expired: Boolean) = ExpenseView(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status,
    expired = expired
)
