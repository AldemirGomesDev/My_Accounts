package br.com.aldemir.data.repository.expense

import br.com.aldemir.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.data.mapper.toDto
import br.com.aldemir.data.mapper.toExpenseMonthlyDomain
import br.com.aldemir.data.mapper.toExpensePerMonthDomain
import br.com.aldemir.data.mapper.toMonthlyPaymentDomain
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class MonthlyPaymentRepositoryImpl(
    private val monthlyPaymentDao: MonthlyPaymentDao
): MonthlyPaymentRepository {
    override suspend fun insertMonthlyPayment(expenseMonthly: ExpenseMonthlyDomain): Long {
        return monthlyPaymentDao.insert(expenseMonthly.toDto())
    }

    override suspend fun updateSituationById(id: Int, situation: Boolean): Int {
        return monthlyPaymentDao.updateSituationById(id, situation)
    }

    override suspend fun updateValue(id: Int, value: Double): Int {
        return monthlyPaymentDao.updateValueById(id, value)
    }

    override suspend fun delete(expenseMonthly: ExpenseMonthlyDomain): Int {
        return monthlyPaymentDao.delete(expenseMonthly.toDto())
    }

    override suspend fun getAllByIdExpense(id: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentDao.getById(id).toMonthlyPaymentDomain()
    }

    override suspend fun getByIdMonthlyPayment(id: Int): ExpenseMonthlyDomain {
        return monthlyPaymentDao.getByIdMonthlyPayment(id).toExpenseMonthlyDomain()
    }

    override suspend fun getAll(): List<ExpenseMonthlyDomain> {
        return monthlyPaymentDao.getAll().toDomain()
    }

    override suspend fun getAllExpensesMonth(month: String, year: String): List<ExpenseMonthlyDomain> {
        return monthlyPaymentDao.getAllExpensesMonth(month, year).toDomain()
    }

    override suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonthDomain> {
        return monthlyPaymentDao.getAllExpensePerMonth(month, year).toExpensePerMonthDomain()
    }
}