package br.com.aldemir.data.database.room.expense

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.data.database.model.ExpenseDTO

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expens: List<br.com.aldemir.data.database.model.ExpenseDTO>): List<Long>

    @Update
    fun update(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Int

    @Delete
    suspend fun delete(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Int

    @Query("SELECT * FROM Expense WHERE id = :id")
    fun getById(id: Int): br.com.aldemir.data.database.model.ExpenseDTO

    @Query("SELECT * FROM Expense")
    suspend fun getAll(): List<br.com.aldemir.data.database.model.ExpenseDTO>

    @Query("SELECT * FROM Expense WHERE name = :name")
    fun getByName(name: String): LiveData<List<br.com.aldemir.data.database.model.ExpenseDTO>>

}