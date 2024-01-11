package br.com.aldemir.data.repository.recipe

class RecipeMonthlyRepositoryImpl constructor(
    private val recipeMonthlyDao: br.com.aldemir.data.database.room.recipe.RecipeMonthlyDao
) : RecipeMonthlyRepository {
    override suspend fun insert(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Long {
        return recipeMonthlyDao.insert(recipeMonthlyDTO)
    }

    override suspend fun update(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Int {
        return recipeMonthlyDao.update(recipeMonthlyDTO)
    }

    override suspend fun delete(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Int {
        return recipeMonthlyDao.delete(recipeMonthlyDTO)
    }

    override suspend fun getAllByIdRecipe(id: Int): List<br.com.aldemir.data.database.model.RecipeMonthlyDomain> {
        return recipeMonthlyDao.getById(id)
    }

    override suspend fun getByIdRecipeMonthly(id: Int): br.com.aldemir.data.database.model.RecipePerMonthDTO {
        return recipeMonthlyDao.getByIdRecipeMonthly(id)
    }

    override suspend fun getAll(): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO> {
        return recipeMonthlyDao.getAll()
    }

    override suspend fun getAllRecipeMonth(month: String, year: String): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO> {
        return recipeMonthlyDao.getAllRecipeMonthly(month, year)
    }

    override suspend fun getAllRecipePerMonth(month: String, year: String): List<br.com.aldemir.data.database.model.RecipePerMonthDTO> {
        return recipeMonthlyDao.getAllRecipePerMonth(month, year)
    }
}