package br.com.aldemir.data.repository.recipe

import br.com.aldemir.data.database.model.RecipeMonthlyDTO
import br.com.aldemir.data.database.model.RecipeMonthlyDomain
import br.com.aldemir.data.database.model.RecipePerMonthDTO

interface RecipeMonthlyRepository {
    suspend fun insert(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Long
    suspend fun update(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Int
    suspend fun delete(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Int
    suspend fun getAllByIdRecipe(id: Int): List<br.com.aldemir.data.database.model.RecipeMonthlyDomain>
    suspend fun getByIdRecipeMonthly(id: Int): br.com.aldemir.data.database.model.RecipePerMonthDTO
    suspend fun getAll(): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO>
    suspend fun getAllRecipeMonth(month: String, year: String): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO>
    suspend fun getAllRecipePerMonth(month: String, year: String): List<br.com.aldemir.data.database.model.RecipePerMonthDTO>
}