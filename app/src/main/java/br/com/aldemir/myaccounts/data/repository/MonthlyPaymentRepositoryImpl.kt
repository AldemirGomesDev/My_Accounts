package br.com.aldemir.myaccounts.data.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment

class MonthlyPaymentRepositoryImpl(private val monthlyPaymentDao: MonthlyPaymentDao): MonthlyPaymentRepository {
    override fun insertExpense(monthlyPayment: MonthlyPayment): Long {
        return monthlyPaymentDao.insert(monthlyPayment)
    }

    override fun update(monthlyPayment: MonthlyPayment): Int {
        return monthlyPaymentDao.update(monthlyPayment)
    }

    override fun delete(monthlyPayment: MonthlyPayment): Int {
        return monthlyPaymentDao.delete(monthlyPayment)
    }

    override fun getAllByIdExpense(id: Int): LiveData<List<MonthlyPayment>> {
        return monthlyPaymentDao.getById(id)
    }

    override fun getByIdMonthlyPayment(id: Int): LiveData<MonthlyPayment> {
        return monthlyPaymentDao.getByIdMonthlyPayment(id)
    }

    override fun getAll(): LiveData<List<MonthlyPayment>> {
        return monthlyPaymentDao.getAll()
    }
}