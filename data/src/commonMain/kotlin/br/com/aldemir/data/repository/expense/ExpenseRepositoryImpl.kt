package br.com.aldemir.data.repository.expense

import br.com.aldemir.data.database.room.expense.ExpenseDao
import br.com.aldemir.data.mapper.toDto
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.repository.ExpenseRepository

class ExpenseRepositoryImpl constructor(
    private val expenseDao: ExpenseDao
): ExpenseRepository {
    override suspend fun insertExpense(expense: ExpenseDomain): Long {
        return expenseDao.insert(expense.toDto())
    }

    override fun update(expense: ExpenseDomain): Int {
        return expenseDao.update(expense.toDto())
    }

    override suspend fun delete(expense: ExpenseDomain): Int {
        return expenseDao.delete(expense.toDto())
    }

    override suspend fun getAll(): List<ExpenseDomain> {
        return expenseDao.getAll().toDomain()
    }
}