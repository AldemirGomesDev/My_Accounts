package br.com.aldemir.data.repository.expense

import br.com.aldemir.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.data.mapper.toDto
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class MonthlyPaymentRepositoryImpl constructor(
    private val monthlyPaymentDao: MonthlyPaymentDao
): MonthlyPaymentRepository {
    override suspend fun insertMonthlyPayment(expenseMonthly: ExpenseMonthlyDomain): Long {
        return monthlyPaymentDao.insert(expenseMonthly.toDto())
    }

    override suspend fun update(expenseMonthly: ExpenseMonthlyDomain): Int {
        return monthlyPaymentDao.update(expenseMonthly.toDto())
    }

    override suspend fun delete(expenseMonthly: ExpenseMonthlyDomain): Int {
        return monthlyPaymentDao.delete(expenseMonthly.toDto())
    }

    override suspend fun getAllByIdExpense(id: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentDao.getById(id).toDomain()
    }

    override suspend fun getByIdMonthlyPayment(id: Int): ExpenseMonthlyDomain {
        return monthlyPaymentDao.getByIdMonthlyPayment(id).toDomain()
    }

    override suspend fun getAll(): List<ExpenseMonthlyDomain> {
        return monthlyPaymentDao.getAll().toDomain()
    }

    override suspend fun getAllExpensesMonth(month: String, year: String): List<ExpenseMonthlyDomain> {
        return monthlyPaymentDao.getAllExpensesMonth(month, year).toDomain()
    }

    override suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonthDomain> {
        return monthlyPaymentDao.getAllExpensePerMonth(month, year).toDomain()
    }
}