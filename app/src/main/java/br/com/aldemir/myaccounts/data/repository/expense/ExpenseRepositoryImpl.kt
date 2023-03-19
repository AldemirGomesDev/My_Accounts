package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.model.ExpenseDTO
import br.com.aldemir.myaccounts.data.database.room.expense.ExpenseDao
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
): ExpenseRepository {
    override suspend fun insertExpense(expenseDTO: ExpenseDTO): Long {
        return expenseDao.insert(expenseDTO)
    }

    override fun update(expenseDTO: ExpenseDTO): Int {
        return expenseDao.update(expenseDTO)
    }

    override suspend fun delete(expenseDTO: ExpenseDTO): Int {
        return expenseDao.delete(expenseDTO)
    }

    override suspend fun getAll(): List<ExpenseDTO> {
        return expenseDao.getAll()
    }
}