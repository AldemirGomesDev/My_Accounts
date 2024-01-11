package br.com.aldemir.data.repository.expense

class ExpenseRepositoryImpl constructor(
    private val expenseDao: br.com.aldemir.data.database.room.expense.ExpenseDao
): ExpenseRepository {
    override suspend fun insertExpense(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Long {
        return expenseDao.insert(expenseDTO)
    }

    override fun update(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Int {
        return expenseDao.update(expenseDTO)
    }

    override suspend fun delete(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Int {
        return expenseDao.delete(expenseDTO)
    }

    override suspend fun getAll(): List<br.com.aldemir.data.database.model.ExpenseDTO> {
        return expenseDao.getAll()
    }
}