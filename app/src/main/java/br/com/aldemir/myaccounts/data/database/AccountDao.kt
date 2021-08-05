package br.com.aldemir.myaccounts.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(accounts: List<Account>): List<Long>

    @Update
    fun update(account: Account): Int

    @Delete
    fun delete(account: Account)

    @Query("SELECT * FROM Account WHERE id = :id")
    fun getById(id: Int): Account

    @Query("SELECT * FROM Account")
    fun getAll(): LiveData<List<Account>>

    @Query("SELECT * FROM Account WHERE name = :name")
    fun getByName(name: String): LiveData<List<Account>>



}