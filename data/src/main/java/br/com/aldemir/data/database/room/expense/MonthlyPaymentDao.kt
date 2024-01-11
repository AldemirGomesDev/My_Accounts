package br.com.aldemir.data.database.room.expense

import androidx.room.*
import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.MonthlyPaymentDomain

@Dao
interface MonthlyPaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseMonthlyDTO: ExpenseMonthlyDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenseMonthlyDTOS: List<ExpenseMonthlyDTO>): List<Long>

    @Update
    suspend fun update(expenseMonthlyDTO: ExpenseMonthlyDTO): Int

    @Delete
    suspend fun delete(expenseMonthlyDTO: ExpenseMonthlyDTO): Int

    @Query("SELECT M.id, M.id_expense, M.year, M.month, M.value, E.due_date, M.situation " +
            "FROM monthly_payment as M INNER JOIN Expense as E on M.id_expense = E.id WHERE M.id_expense = :id")
    suspend fun getById(id: Int): List<MonthlyPaymentDomain>

    @Query("SELECT * FROM monthly_payment WHERE id = :id")
    suspend fun getByIdMonthlyPayment(id: Int): ExpenseMonthlyDTO

    @Query("SELECT * FROM monthly_payment WHERE month = :month AND year = :year")
    suspend fun getAllExpensesMonth(month: String, year: String): List<ExpenseMonthlyDTO>

    @Query("SELECT M.id_expense, E.name, E.description, E.due_date, M.year, M.month, M.value, M.situation " +
            "FROM Expense as E INNER JOIN monthly_payment as M on E.id = M.id_expense WHERE M.month = :month AND M.year = :year")
    suspend fun getAllExpensePerMonth(month: String, year: String): List<ExpensePerMonthDTO>

    @Query("SELECT * FROM monthly_payment")
    suspend fun getAll(): List<ExpenseMonthlyDTO>


}