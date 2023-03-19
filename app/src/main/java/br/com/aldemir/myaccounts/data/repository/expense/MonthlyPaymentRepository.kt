package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.model.ExpensePerMonthDTO
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain

interface MonthlyPaymentRepository {
    suspend fun insertMonthlyPayment(expenseMonthlyDTO: ExpenseMonthlyDTO): Long
    suspend fun update(expenseMonthlyDTO: ExpenseMonthlyDTO): Int
    suspend fun delete(expenseMonthlyDTO: ExpenseMonthlyDTO): Int
    suspend fun getAllByIdExpense(id: Int): List<MonthlyPaymentDomain>
    suspend fun getByIdMonthlyPayment(id: Int): ExpenseMonthlyDTO
    suspend fun getAll(): List<ExpenseMonthlyDTO>
    suspend fun getAllExpensesMonth(month: String, year: String): List<ExpenseMonthlyDTO>
    suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonthDTO>
}