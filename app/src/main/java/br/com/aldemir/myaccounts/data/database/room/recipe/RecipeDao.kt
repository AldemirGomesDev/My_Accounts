package br.com.aldemir.myaccounts.data.database.room.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.myaccounts.data.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<Recipe>): List<Long>

    @Update
    fun update(recipe: Recipe): Int

    @Delete
    suspend fun delete(recipe: Recipe): Int

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getById(id: Int): Recipe

    @Query("SELECT * FROM recipe")
    suspend fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE name = :name")
    fun getByName(name: String): LiveData<List<Recipe>>
}