package br.com.aldemir.domain.repository

import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.model.RecipeUpdateDomain

interface RecipeRepository {
    suspend fun insert(recipe: RecipeDomain): Long
    fun update(recipe: RecipeDomain): Int
    suspend fun updateNameDescription(recipeUpdate: RecipeUpdateDomain): Int
    suspend fun delete(recipe: RecipeDomain): Int
    suspend fun getAll(): List<RecipeDomain>
}