package br.com.aldemir.domain.repository

import br.com.aldemir.domain.model.ExpenseDomain

interface ExpenseRepository {
    suspend fun insertExpense(expense: ExpenseDomain): Long
    fun update(expense: ExpenseDomain): Int
    suspend fun delete(expense: ExpenseDomain): Int
    suspend fun getAll(): List<ExpenseDomain>
}