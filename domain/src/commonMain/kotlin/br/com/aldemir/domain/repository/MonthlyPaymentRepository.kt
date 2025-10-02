package br.com.aldemir.domain.repository

import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.model.MonthlyPaymentDomain

interface MonthlyPaymentRepository {
    suspend fun insertMonthlyPayment(expenseMonthly: ExpenseMonthlyDomain): Long
    suspend fun updateSituationById(id: Int, situation: Boolean): Int
    suspend fun updateValue(id: Int, value: Double): Int
    suspend fun delete(expenseMonthly: ExpenseMonthlyDomain): Int
    suspend fun getAllByIdExpense(id: Int): List<MonthlyPaymentDomain>
    suspend fun getByIdMonthlyPayment(id: Int): ExpenseMonthlyDomain
    suspend fun getAll(): List<ExpenseMonthlyDomain>
    suspend fun getAllExpensesMonth(month: String, year: String): List<ExpenseMonthlyDomain>
    suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonthDomain>
}