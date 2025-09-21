package br.com.aldemir.data.database.room.expense

import androidx.room.*
import br.com.aldemir.data.database.model.ExpenseDTO

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseDTO: ExpenseDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expens: List<ExpenseDTO>): List<Long>

    @Update
    suspend fun update(expenseDTO: ExpenseDTO): Int

    @Delete
    suspend fun delete(expenseDTO: ExpenseDTO): Int

    @Query("SELECT * FROM Expense WHERE id = :id")
    suspend fun getById(id: Int): ExpenseDTO

    @Query("SELECT * FROM Expense")
    suspend fun getAll(): List<ExpenseDTO>
}