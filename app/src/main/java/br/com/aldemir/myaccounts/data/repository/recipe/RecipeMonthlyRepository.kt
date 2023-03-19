package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth

interface RecipeMonthlyRepository {
    suspend fun insert(recipeMonthly: RecipeMonthly): Long
    suspend fun update(recipeMonthly: RecipeMonthly): Int
    suspend fun delete(recipeMonthly: RecipeMonthly): Int
    suspend fun getAllByIdRecipe(id: Int): List<RecipeMonthlyDomain>
    suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonth
    suspend fun getAll(): List<RecipeMonthly>
    suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthly>
    suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonth>
}