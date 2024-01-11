package br.com.aldemir.data.repository.recipe

import br.com.aldemir.data.database.model.RecipeDTO
import br.com.aldemir.data.database.model.RecipeUpdateDTO

interface RecipeRepository {
    suspend fun insert(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Long
    fun update(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int
    suspend fun updateNameDescription(recipeDTO: br.com.aldemir.data.database.model.RecipeUpdateDTO): Int
    suspend fun delete(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int
    suspend fun getAll(): List<br.com.aldemir.data.database.model.RecipeDTO>
}