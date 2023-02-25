package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.model.ExpensePerMonth
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain

interface MonthlyPaymentRepository {
    suspend fun insertMonthlyPayment(monthlyPayment: MonthlyPayment): Long
    suspend fun update(monthlyPayment: MonthlyPayment): Int
    suspend fun delete(monthlyPayment: MonthlyPayment): Int
    suspend fun getAllByIdExpense(id: Int): List<MonthlyPaymentDomain>
    suspend fun getByIdMonthlyPayment(id: Int): MonthlyPayment
    suspend fun getAll(): List<MonthlyPayment>
    suspend fun getAllExpensesMonth(month: String, year: String): List<MonthlyPayment>
    suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonth>
}