package br.com.aldemir.myaccounts.data.repository.expense

import br.com.aldemir.myaccounts.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.model.ExpensePerMonthDTO
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain
import javax.inject.Inject

class MonthlyPaymentRepositoryImpl @Inject constructor(
    private val monthlyPaymentDao: MonthlyPaymentDao
): MonthlyPaymentRepository {
    override suspend fun insertMonthlyPayment(expenseMonthlyDTO: ExpenseMonthlyDTO): Long {
        return monthlyPaymentDao.insert(expenseMonthlyDTO)
    }

    override suspend fun update(expenseMonthlyDTO: ExpenseMonthlyDTO): Int {
        return monthlyPaymentDao.update(expenseMonthlyDTO)
    }

    override suspend fun delete(expenseMonthlyDTO: ExpenseMonthlyDTO): Int {
        return monthlyPaymentDao.delete(expenseMonthlyDTO)
    }

    override suspend fun getAllByIdExpense(id: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentDao.getById(id)
    }

    override suspend fun getByIdMonthlyPayment(id: Int): ExpenseMonthlyDTO {
        return monthlyPaymentDao.getByIdMonthlyPayment(id)
    }

    override suspend fun getAll(): List<ExpenseMonthlyDTO> {
        return monthlyPaymentDao.getAll()
    }

    override suspend fun getAllExpensesMonth(month: String, year: String): List<ExpenseMonthlyDTO> {
        return monthlyPaymentDao.getAllExpensesMonth(month, year)
    }

    override suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonthDTO> {
        return monthlyPaymentDao.getAllExpensePerMonth(month, year)
    }
}