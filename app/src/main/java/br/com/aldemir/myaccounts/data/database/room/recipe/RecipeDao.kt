package br.com.aldemir.myaccounts.data.database.room.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.aldemir.myaccounts.data.model.RecipeDTO
import br.com.aldemir.myaccounts.data.model.RecipeUpdateDTO

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeDTO: RecipeDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipeDTOS: List<RecipeDTO>): List<Long>

    @Update
    fun update(recipeDTO: RecipeDTO): Int

    @Update(entity = RecipeDTO::class)
    suspend fun updateNameDescription(recipeDTO: RecipeUpdateDTO): Int

    @Delete
    suspend fun delete(recipeDTO: RecipeDTO): Int

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getById(id: Int): RecipeDTO

    @Query("SELECT * FROM recipe")
    suspend fun getAll(): List<RecipeDTO>

    @Query("SELECT * FROM recipe WHERE name = :name")
    fun getByName(name: String): LiveData<List<RecipeDTO>>
}