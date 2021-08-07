package br.com.aldemir.myaccounts.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment

@Dao
interface MonthlyPaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(monthlyPayment: MonthlyPayment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(monthlyPayments: List<MonthlyPayment>): List<Long>

    @Update
    fun update(monthlyPayment: MonthlyPayment): Int

    @Delete
    fun delete(monthlyPayment: MonthlyPayment): Int

    @Query("SELECT * FROM monthly_payment WHERE id_expense = :id")
    fun getById(id: Int): LiveData<List<MonthlyPayment>>

    @Query("SELECT * FROM monthly_payment")
    fun getAll(): LiveData<List<MonthlyPayment>>


}