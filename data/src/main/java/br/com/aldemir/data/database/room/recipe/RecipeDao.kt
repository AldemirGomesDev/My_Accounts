package br.com.aldemir.data.database.room.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.data.database.model.RecipeDTO
import br.com.aldemir.data.database.model.RecipeUpdateDTO

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipeDTOS: List<br.com.aldemir.data.database.model.RecipeDTO>): List<Long>

    @Update
    fun update(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int

    @Update(entity = br.com.aldemir.data.database.model.RecipeDTO::class)
    suspend fun updateNameDescription(recipeDTO: br.com.aldemir.data.database.model.RecipeUpdateDTO): Int

    @Delete
    suspend fun delete(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getById(id: Int): br.com.aldemir.data.database.model.RecipeDTO

    @Query("SELECT * FROM recipe")
    suspend fun getAll(): List<br.com.aldemir.data.database.model.RecipeDTO>

    @Query("SELECT * FROM recipe WHERE name = :name")
    fun getByName(name: String): LiveData<List<br.com.aldemir.data.database.model.RecipeDTO>>
}