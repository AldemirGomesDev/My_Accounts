package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeDao
import br.com.aldemir.myaccounts.data.model.RecipeDTO
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {
    override suspend fun insert(recipeDTO: RecipeDTO): Long {
        return recipeDao.insert(recipeDTO)
    }

    override fun update(recipeDTO: RecipeDTO): Int {
        return recipeDao.update(recipeDTO)
    }

    override suspend fun delete(recipeDTO: RecipeDTO): Int {
        return recipeDao.delete(recipeDTO)
    }

    override suspend fun getAll(): List<RecipeDTO> {
        return recipeDao.getAll()
    }
}