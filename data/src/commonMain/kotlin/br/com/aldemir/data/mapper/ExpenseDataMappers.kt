package br.com.aldemir.data.mapper

import br.com.aldemir.data.database.model.ExpenseDTO
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import br.com.aldemir.data.database.model.MonthlyPaymentDTO
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.model.MonthlyPaymentDomain

fun ExpenseDomain.toDto() = ExpenseDTO(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)

@JvmName("ExpenseDTOToDomain")
fun List<ExpenseDTO>.toDomain(): List<ExpenseDomain> {
    return this.map {
        ExpenseDomain(
            id = it.id,
            name = it.name,
            description = it.description,
            created_at = it.created_at,
            due_date = it.due_date,
            status = it.status
        )
    }
}

fun ExpenseMonthlyDomain.toDto() = ExpenseMonthlyDTO(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    situation = situation
)

@JvmName("MonthlyPaymentDTOToDomain")
fun List<MonthlyPaymentDTO>.toDomain(): List<MonthlyPaymentDomain> {
    return this.map {
        MonthlyPaymentDomain(
            id = it.id,
            id_expense = it.id_expense,
            year = it.year,
            month = it.month,
            value = it.value,
            due_date = it.due_date,
            situation = it.situation
        )
    }
}

fun ExpenseMonthlyDTO.toDomain() = ExpenseMonthlyDomain(
    id = id,
    id_expense = id_expense,
    year = year,
    month = month,
    value = value,
    situation = situation
)

@JvmName("ExpenseMonthlyDTOToDomain")
fun List<ExpenseMonthlyDTO>.toDomain(): List<ExpenseMonthlyDomain> {
    return this.map {
        ExpenseMonthlyDomain(
            id = it.id,
            id_expense = it.id_expense,
            year = it.year,
            month = it.month,
            value = it.value,
            situation = it.situation
        )
    }
}

@JvmName("ExpensePerMonthDTOToDomain")
fun List<ExpensePerMonthDTO>.toDomain(): List<ExpensePerMonthDomain> {
    return this.map {
        ExpensePerMonthDomain(
            id_expense = it.id_expense,
            name = it.name,
            description = it.description,
            due_date = it.due_date,
            year = it.year,
            month = it.month,
            value = it.value,
            situation = it.situation
        )
    }
}

