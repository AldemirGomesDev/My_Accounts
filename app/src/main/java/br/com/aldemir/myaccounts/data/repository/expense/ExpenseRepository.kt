package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.model.Expense

interface ExpenseRepository {
    suspend fun insertExpense(expense: Expense): Long
    fun update(expense: Expense): Int
    suspend fun delete(expense: Expense): Int
    suspend fun getAll(): List<Expense>
}