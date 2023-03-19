package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeMonthlyDao
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import javax.inject.Inject

class RecipeMonthlyRepositoryImpl @Inject constructor(
    private val recipeMonthlyDao: RecipeMonthlyDao
) : RecipeMonthlyRepository {
    override suspend fun insert(recipeMonthly: RecipeMonthly): Long {
        return recipeMonthlyDao.insert(recipeMonthly)
    }

    override suspend fun update(recipeMonthly: RecipeMonthly): Int {
        return recipeMonthlyDao.update(recipeMonthly)
    }

    override suspend fun delete(recipeMonthly: RecipeMonthly): Int {
        return recipeMonthlyDao.delete(recipeMonthly)
    }

    override suspend fun getAllByIdRecipe(id: Int): List<RecipeMonthlyDomain> {
        return recipeMonthlyDao.getById(id)
    }

    override suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonth {
        return recipeMonthlyDao.getByIdRecipeMonthly(id)
    }

    override suspend fun getAll(): List<RecipeMonthly> {
        return recipeMonthlyDao.getAll()
    }

    override suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthly> {
        return recipeMonthlyDao.getAllRecipeMonthly(month, year)
    }

    override suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonth> {
        return recipeMonthlyDao.getAllRecipePerMonth(month, year)
    }
}