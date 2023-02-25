package br.com.aldemir.myaccounts.data.database.room.expense

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.myaccounts.data.model.Expense

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expenses: List<Expense>): List<Long>

    @Update
    fun update(expense: Expense): Int

    @Delete
    suspend fun delete(expense: Expense): Int

    @Query("SELECT * FROM Expense WHERE id = :id")
    fun getById(id: Int): Expense

    @Query("SELECT * FROM Expense")
    suspend fun getAll(): List<Expense>

    @Query("SELECT * FROM Expense WHERE name = :name")
    fun getByName(name: String): LiveData<List<Expense>>

}