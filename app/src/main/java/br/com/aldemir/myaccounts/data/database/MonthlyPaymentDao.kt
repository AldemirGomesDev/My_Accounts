package br.com.aldemir.myaccounts.data.database

import androidx.room.*
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

@Dao
interface MonthlyPaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monthlyPayment: MonthlyPayment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(monthlyPayments: List<MonthlyPayment>): List<Long>

    @Update
    suspend fun update(monthlyPayment: MonthlyPayment): Int

    @Delete
    suspend fun delete(monthlyPayment: MonthlyPayment): Int

    @Query("SELECT * FROM monthly_payment WHERE id_expense = :id")
    suspend fun getById(id: Int): List<MonthlyPayment>

    @Query("SELECT * FROM monthly_payment WHERE id = :id")
    suspend fun getByIdMonthlyPayment(id: Int): MonthlyPayment

    @Query("SELECT * FROM monthly_payment WHERE month = :month AND year = :year")
    suspend fun getAllExpensesMonth(month: String, year: String): List<MonthlyPayment>

    @Query("SELECT * FROM monthly_payment")
    suspend fun getAll(): List<MonthlyPayment>


}