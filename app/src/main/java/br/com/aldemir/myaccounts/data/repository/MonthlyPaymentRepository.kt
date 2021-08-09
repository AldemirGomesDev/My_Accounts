package br.com.aldemir.myaccounts.data.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment

interface MonthlyPaymentRepository {
    fun insertExpense(monthlyPayment: MonthlyPayment): Long
    fun update(monthlyPayment: MonthlyPayment): Int
    fun delete(monthlyPayment: MonthlyPayment): Int
    fun getAllByIdExpense(id: Int): LiveData<List<MonthlyPayment>>
    fun getByIdMonthlyPayment(id: Int): LiveData<MonthlyPayment>
    fun getAll(): LiveData<List<MonthlyPayment>>
}