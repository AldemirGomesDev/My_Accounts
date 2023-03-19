package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.model.RecipeDTO
import br.com.aldemir.myaccounts.data.model.RecipeUpdateDTO

interface RecipeRepository {
    suspend fun insert(recipeDTO: RecipeDTO): Long
    fun update(recipeDTO: RecipeDTO): Int
    suspend fun updateNameDescription(recipeDTO: RecipeUpdateDTO): Int
    suspend fun delete(recipeDTO: RecipeDTO): Int
    suspend fun getAll(): List<RecipeDTO>
}