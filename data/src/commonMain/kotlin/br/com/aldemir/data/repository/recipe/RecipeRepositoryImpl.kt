package br.com.aldemir.data.repository.recipe

import br.com.aldemir.data.database.room.recipe.RecipeDao
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.data.mapper.toDto
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.repository.RecipeRepository

class RecipeRepositoryImpl constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {
    override suspend fun insert(recipe: RecipeDomain): Long {
        return recipeDao.insert(recipe.toDto())
    }

    override fun update(recipe: RecipeDomain): Int {
        return recipeDao.update(recipe.toDto())
    }

    override suspend fun updateNameDescription(recipeUpdate: RecipeUpdateDomain): Int {
        return recipeDao.updateNameDescription(recipeUpdate.toDto())
    }

    override suspend fun delete(recipe: RecipeDomain): Int {
        return recipeDao.delete(recipe.toDto())
    }

    override suspend fun getAll(): List<RecipeDomain> {
        return recipeDao.getAll().toDomain()
    }
}