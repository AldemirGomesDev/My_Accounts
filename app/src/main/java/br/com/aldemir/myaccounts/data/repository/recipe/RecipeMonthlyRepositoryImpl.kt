package br.com.aldemir.myaccounts.data.repository.recipe

import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeMonthlyDao
import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO
import javax.inject.Inject

class RecipeMonthlyRepositoryImpl @Inject constructor(
    private val recipeMonthlyDao: RecipeMonthlyDao
) : RecipeMonthlyRepository {
    override suspend fun insert(recipeMonthlyDTO: RecipeMonthlyDTO): Long {
        return recipeMonthlyDao.insert(recipeMonthlyDTO)
    }

    override suspend fun update(recipeMonthlyDTO: RecipeMonthlyDTO): Int {
        return recipeMonthlyDao.update(recipeMonthlyDTO)
    }

    override suspend fun delete(recipeMonthlyDTO: RecipeMonthlyDTO): Int {
        return recipeMonthlyDao.delete(recipeMonthlyDTO)
    }

    override suspend fun getAllByIdRecipe(id: Int): List<RecipeMonthlyDomain> {
        return recipeMonthlyDao.getById(id)
    }

    override suspend fun getByIdRecipeMonthly(id: Int): RecipePerMonthDTO {
        return recipeMonthlyDao.getByIdRecipeMonthly(id)
    }

    override suspend fun getAll(): List<RecipeMonthlyDTO> {
        return recipeMonthlyDao.getAll()
    }

    override suspend fun getAllRecipeMonth(month: String, year: String): List<RecipeMonthlyDTO> {
        return recipeMonthlyDao.getAllRecipeMonthly(month, year)
    }

    override suspend fun getAllRecipePerMonth(month: String, year: String): List<RecipePerMonthDTO> {
        return recipeMonthlyDao.getAllRecipePerMonth(month, year)
    }
}