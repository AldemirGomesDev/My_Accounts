package br.com.aldemir.data.repository.recipe

import br.com.aldemir.data.database.room.recipe.RecipeMonthlyDao
import br.com.aldemir.data.mapper.toDTO
import br.com.aldemir.data.mapper.toRecipeMonthlyDomain
import br.com.aldemir.data.mapper.toRecipePerMonthDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class RecipeMonthlyRepositoryImpl(
    private val recipeMonthlyDao: RecipeMonthlyDao
) : RecipeMonthlyRepository {
    override suspend fun insert(recipeMonthly: RecipeMonthlyDomain): Long {
        return recipeMonthlyDao.insert(recipeMonthly.toDTO())
    }

    override suspend fun update(id: Int, situation: Boolean): Int {
        return recipeMonthlyDao.updateSituationById(id, situation)
    }

    override suspend fun updateValueById(id: Int, value: Double): Int {
        return recipeMonthlyDao.updateValueById(id, value)
    }

    override suspend fun delete(recipeMonthly: RecipeMonthlyDomain): Int {
        return recipeMonthlyDao.delete(recipeMonthly.toDTO())
    }

    override suspend fun getAllByIdRecipe(id: Int): List<RecipePerMonthDomain> {
        return recipeMonthlyDao.getById(id).toRecipePerMonthDomain()
    }

    override suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonthDomain {
        return recipeMonthlyDao.getByIdRecipeMonthly(id).toRecipePerMonthDomain()
    }

    override suspend fun getAll(): List<RecipeMonthlyDomain> {
        return recipeMonthlyDao.getAll().toRecipeMonthlyDomain()
    }

    override suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthlyDomain> {
        return recipeMonthlyDao.getAllRecipeMonthly(month, year).toRecipeMonthlyDomain()
    }

    override suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonthDomain> {
        return recipeMonthlyDao.getAllRecipePerMonth(month, year).toRecipePerMonthDomain()
    }
}