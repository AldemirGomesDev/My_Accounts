package br.com.aldemir.myaccounts.data.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

interface MonthlyPaymentRepository {
    suspend fun insertMonthlyPayment(monthlyPayment: MonthlyPayment): Long
    suspend fun update(monthlyPayment: MonthlyPayment): Int
    suspend fun delete(monthlyPayment: MonthlyPayment): Int
    suspend fun getAllByIdExpense(id: Int): List<MonthlyPayment>
    suspend fun getByIdMonthlyPayment(id: Int): MonthlyPayment
    suspend fun getAll(): List<MonthlyPayment>
    suspend fun getAllExpensesMonth(month: String, year: String): List<MonthlyPayment>
    suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonth>
}