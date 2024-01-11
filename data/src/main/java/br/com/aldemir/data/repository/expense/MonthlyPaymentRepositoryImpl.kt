package br.com.aldemir.data.repository.expense

import br.com.aldemir.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

class MonthlyPaymentRepositoryImpl constructor(
    private val monthlyPaymentDao: MonthlyPaymentDao
): MonthlyPaymentRepository {
    override suspend fun insertMonthlyPayment(expenseMonthlyDTO: ExpenseMonthlyDTO): Long {
        return monthlyPaymentDao.insert(expenseMonthlyDTO)
    }

    override suspend fun update(expenseMonthlyDTO: ExpenseMonthlyDTO): Int {
        return monthlyPaymentDao.update(expenseMonthlyDTO)
    }

    override suspend fun delete(expenseMonthlyDTO: ExpenseMonthlyDTO): Int {
        return monthlyPaymentDao.delete(expenseMonthlyDTO)
    }

    override suspend fun getAllByIdExpense(id: Int): List<br.com.aldemir.data.database.model.MonthlyPaymentDomain> {
        return monthlyPaymentDao.getById(id)
    }

    override suspend fun getByIdMonthlyPayment(id: Int): br.com.aldemir.data.database.model.ExpenseMonthlyDTO {
        return monthlyPaymentDao.getByIdMonthlyPayment(id)
    }

    override suspend fun getAll(): List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO> {
        return monthlyPaymentDao.getAll()
    }

    override suspend fun getAllExpensesMonth(month: String, year: String): List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO> {
        return monthlyPaymentDao.getAllExpensesMonth(month, year)
    }

    override suspend fun getAllExpensePerMonth(month: String, year: String): List<br.com.aldemir.data.database.model.ExpensePerMonthDTO> {
        return monthlyPaymentDao.getAllExpensePerMonth(month, year)
    }
}