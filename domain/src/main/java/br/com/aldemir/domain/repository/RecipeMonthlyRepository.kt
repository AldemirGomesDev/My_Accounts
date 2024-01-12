package br.com.aldemir.domain.repository

import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain

interface RecipeMonthlyRepository {
    suspend fun insert(recipeMonthly: RecipeMonthlyDomain): Long
    suspend fun update(recipeMonthly: RecipeMonthlyDomain): Int
    suspend fun delete(recipeMonthly: RecipeMonthlyDomain): Int
    suspend fun getAllByIdRecipe(id: Int): List<RecipePerMonthDomain>
    suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonthDomain
    suspend fun getAll(): List<RecipeMonthlyDomain>
    suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthlyDomain>
    suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonthDomain>
}