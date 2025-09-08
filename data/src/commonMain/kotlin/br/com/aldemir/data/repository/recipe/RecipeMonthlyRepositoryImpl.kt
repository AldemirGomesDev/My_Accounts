package br.com.aldemir.data.repository.recipe

import br.com.aldemir.data.database.room.recipe.RecipeMonthlyDao
import br.com.aldemir.data.mapper.toDTO
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class RecipeMonthlyRepositoryImpl constructor(
    private val recipeMonthlyDao: RecipeMonthlyDao
) : RecipeMonthlyRepository {
    override suspend fun insert(recipeMonthly: RecipeMonthlyDomain): Long {
        return recipeMonthlyDao.insert(recipeMonthly.toDTO())
    }

    override suspend fun update(recipeMonthly: RecipeMonthlyDomain): Int {
        return recipeMonthlyDao.update(recipeMonthly.toDTO())
    }

    override suspend fun delete(recipeMonthly: RecipeMonthlyDomain): Int {
        return recipeMonthlyDao.delete(recipeMonthly.toDTO())
    }

    override suspend fun getAllByIdRecipe(id: Int): List<RecipePerMonthDomain> {
        return recipeMonthlyDao.getById(id).toDomain()
    }

    override suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonthDomain {
        return recipeMonthlyDao.getByIdRecipeMonthly(id).toDomain()
    }

    override suspend fun getAll(): List<RecipeMonthlyDomain> {
        return recipeMonthlyDao.getAll().toDomain()
    }

    override suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthlyDomain> {
        return recipeMonthlyDao.getAllRecipeMonthly(month, year).toDomain()
    }

    override suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonthDomain> {
        return recipeMonthlyDao.getAllRecipePerMonth(month, year).toDomain()
    }
}