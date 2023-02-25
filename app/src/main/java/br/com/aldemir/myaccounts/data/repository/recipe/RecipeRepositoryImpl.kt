package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeDao
import br.com.aldemir.myaccounts.data.model.Recipe
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {
    override suspend fun insert(recipe: Recipe): Long {
        return recipeDao.insert(recipe)
    }

    override fun update(recipe: Recipe): Int {
        return recipeDao.update(recipe)
    }

    override suspend fun delete(recipe: Recipe): Int {
        return recipeDao.delete(recipe)
    }

    override suspend fun getAll(): List<Recipe> {
        return recipeDao.getAll()
    }
}