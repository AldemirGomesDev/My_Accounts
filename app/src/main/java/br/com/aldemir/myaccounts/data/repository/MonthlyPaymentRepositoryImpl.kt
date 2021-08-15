package br.com.aldemir.myaccounts.data.repository

import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import javax.inject.Inject

class MonthlyPaymentRepositoryImpl @Inject constructor(
    private val monthlyPaymentDao: MonthlyPaymentDao
): MonthlyPaymentRepository {
    override suspend fun insertMonthlyPayment(monthlyPayment: MonthlyPayment): Long {
        return monthlyPaymentDao.insert(monthlyPayment)
    }

    override suspend fun update(monthlyPayment: MonthlyPayment): Int {
        return monthlyPaymentDao.update(monthlyPayment)
    }

    override suspend fun delete(monthlyPayment: MonthlyPayment): Int {
        return monthlyPaymentDao.delete(monthlyPayment)
    }

    override suspend fun getAllByIdExpense(id: Int): List<MonthlyPayment> {
        return monthlyPaymentDao.getById(id)
    }

    override suspend fun getByIdMonthlyPayment(id: Int): MonthlyPayment {
        return monthlyPaymentDao.getByIdMonthlyPayment(id)
    }

    override suspend fun getAll(): List<MonthlyPayment> {
        return monthlyPaymentDao.getAll()
    }

    override suspend fun getAllExpensesMonth(month: String, year: String): List<MonthlyPayment> {
        return monthlyPaymentDao.getAllExpensesMonth(month, year)
    }
}