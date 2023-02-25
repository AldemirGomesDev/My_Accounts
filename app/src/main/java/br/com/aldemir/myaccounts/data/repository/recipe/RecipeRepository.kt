package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.model.Recipe

interface RecipeRepository {
    suspend fun insert(recipe: Recipe): Long
    fun update(recipe: Recipe): Int
    suspend fun delete(recipe: Recipe): Int
    suspend fun getAll(): List<Recipe>
}