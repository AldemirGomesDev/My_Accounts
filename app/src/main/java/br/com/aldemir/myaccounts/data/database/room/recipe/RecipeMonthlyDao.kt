package br.com.aldemir.myaccounts.data.database.room.recipe

import androidx.room.*
import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO

@Dao
interface RecipeMonthlyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeMonthlyDTO: RecipeMonthlyDTO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipeMonthlies: List<RecipeMonthlyDTO>): List<Long>

    @Update
    suspend fun update(recipeMonthlyDTO: RecipeMonthlyDTO): Int

    @Delete
    suspend fun delete(recipeMonthlyDTO: RecipeMonthlyDTO): Int

    @Query("SELECT M.id, M.id_recipe, E.name, M.year, M.month, M.value, E.due_date, M.status " +
            "FROM recipe_monthly as M INNER JOIN recipe as E on M.id_recipe = E.id WHERE M.id_recipe = :id")
    suspend fun getById(id: Int): List<RecipeMonthlyDomain>

    @Query("SELECT M.id, M.id_recipe, E.name, E.description, E.due_date, M.year, M.month, M.value, M.status " +
            "FROM recipe as E INNER JOIN recipe_monthly as M on E.id = M.id_recipe WHERE M.id = :id")
    suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonthDTO

    @Query("SELECT * FROM recipe_monthly WHERE month = :month AND year = :year")
    suspend fun getAllRecipeMonthly(month: String, year: String): List<RecipeMonthlyDTO>

    @Query("SELECT E.id, M.id_recipe, E.name, E.description, E.due_date, M.year, M.month, M.value, M.status " +
            "FROM recipe as E INNER JOIN recipe_monthly as M on E.id = M.id_recipe WHERE M.month = :month AND M.year = :year")
    suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonthDTO>

    @Query("SELECT * FROM recipe_monthly")
    suspend fun getAll(): List<RecipeMonthlyDTO>
}