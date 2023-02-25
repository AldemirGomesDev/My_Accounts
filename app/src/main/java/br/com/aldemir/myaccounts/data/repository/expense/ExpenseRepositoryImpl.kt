package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.model.Expense
import br.com.aldemir.myaccounts.data.database.room.expense.ExpenseDao
import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
): ExpenseRepository {
    override suspend fun insertExpense(expense: Expense): Long {
        return expenseDao.insert(expense)
    }

    override fun update(expense: Expense): Int {
        return expenseDao.update(expense)
    }

    override suspend fun delete(expense: Expense): Int {
        return expenseDao.delete(expense)
    }

    override suspend fun getAll(): List<Expense> {
        return expenseDao.getAll()
    }
}