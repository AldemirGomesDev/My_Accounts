package br.com.aldemir.myaccounts.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.myaccounts.data.domain.model.Expense

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: Expense): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expenses: List<Expense>): List<Long>

    @Update
    fun update(expense: Expense): Int

    @Delete
    fun delete(expense: Expense): Int

    @Query("SELECT * FROM Expense WHERE id = :id")
    fun getById(id: Int): Expense

    @Query("SELECT * FROM Expense")
    fun getAll(): LiveData<List<Expense>>

    @Query("SELECT * FROM Expense WHERE name = :name")
    fun getByName(name: String): LiveData<List<Expense>>

}