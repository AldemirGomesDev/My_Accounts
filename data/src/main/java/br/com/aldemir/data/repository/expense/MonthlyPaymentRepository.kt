package br.com.aldemir.data.repository.expense

import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.MonthlyPaymentDomain

interface MonthlyPaymentRepository {
    suspend fun insertMonthlyPayment(expenseMonthlyDTO: br.com.aldemir.data.database.model.ExpenseMonthlyDTO): Long
    suspend fun update(expenseMonthlyDTO: br.com.aldemir.data.database.model.ExpenseMonthlyDTO): Int
    suspend fun delete(expenseMonthlyDTO: br.com.aldemir.data.database.model.ExpenseMonthlyDTO): Int
    suspend fun getAllByIdExpense(id: Int): List<br.com.aldemir.data.database.model.MonthlyPaymentDomain>
    suspend fun getByIdMonthlyPayment(id: Int): br.com.aldemir.data.database.model.ExpenseMonthlyDTO
    suspend fun getAll(): List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO>
    suspend fun getAllExpensesMonth(month: String, year: String): List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO>
    suspend fun getAllExpensePerMonth(month: String, year: String): List<br.com.aldemir.data.database.model.ExpensePerMonthDTO>
}