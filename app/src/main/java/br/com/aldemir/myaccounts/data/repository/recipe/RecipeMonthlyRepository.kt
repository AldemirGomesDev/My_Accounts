package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO

interface RecipeMonthlyRepository {
    suspend fun insert(recipeMonthlyDTO: RecipeMonthlyDTO): Long
    suspend fun update(recipeMonthlyDTO: RecipeMonthlyDTO): Int
    suspend fun delete(recipeMonthlyDTO: RecipeMonthlyDTO): Int
    suspend fun getAllByIdRecipe(id: Int): List<RecipeMonthlyDomain>
    suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonthDTO
    suspend fun getAll(): List<RecipeMonthlyDTO>
    suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthlyDTO>
    suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonthDTO>
}