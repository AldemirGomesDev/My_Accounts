package br.com.aldemir.myaccounts.data.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.domain.model.Expense

interface ExpenseRepository {
    suspend fun insertExpense(expense: Expense): Long
    fun update(expense: Expense): Int
    suspend fun delete(expense: Expense): Int
    suspend fun getAll(): List<Expense>
}