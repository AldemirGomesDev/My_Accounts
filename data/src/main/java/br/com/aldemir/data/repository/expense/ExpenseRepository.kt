package br.com.aldemir.data.repository.expense

import br.com.aldemir.data.database.model.ExpenseDTO

interface ExpenseRepository {
    suspend fun insertExpense(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Long
    fun update(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Int
    suspend fun delete(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Int
    suspend fun getAll(): List<br.com.aldemir.data.database.model.ExpenseDTO>
}