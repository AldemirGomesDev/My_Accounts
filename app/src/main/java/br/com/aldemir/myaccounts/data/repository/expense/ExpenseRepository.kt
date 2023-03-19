package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.model.ExpenseDTO

interface ExpenseRepository {
    suspend fun insertExpense(expenseDTO: ExpenseDTO): Long
    fun update(expenseDTO: ExpenseDTO): Int
    suspend fun delete(expenseDTO: ExpenseDTO): Int
    suspend fun getAll(): List<ExpenseDTO>
}