package br.com.aldemir.expense.presentation.expensedetail.effect

sealed interface ExpenseDetailEffect {
    data object UpdateSuccess: ExpenseDetailEffect
}