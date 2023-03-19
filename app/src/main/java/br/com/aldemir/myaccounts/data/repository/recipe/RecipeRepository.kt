package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.model.RecipeDTO

interface RecipeRepository {
    suspend fun insert(recipeDTO: RecipeDTO): Long
    fun update(recipeDTO: RecipeDTO): Int
    suspend fun delete(recipeDTO: RecipeDTO): Int
    suspend fun getAll(): List<RecipeDTO>
}