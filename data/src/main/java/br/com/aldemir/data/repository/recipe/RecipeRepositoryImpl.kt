package br.com.aldemir.data.repository.recipe

class RecipeRepositoryImpl constructor(
    private val recipeDao: br.com.aldemir.data.database.room.recipe.RecipeDao
): RecipeRepository {
    override suspend fun insert(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Long {
        return recipeDao.insert(recipeDTO)
    }

    override fun update(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int {
        return recipeDao.update(recipeDTO)
    }

    override suspend fun updateNameDescription(recipeDTO: br.com.aldemir.data.database.model.RecipeUpdateDTO): Int {
        return recipeDao.updateNameDescription(recipeDTO)
    }

    override suspend fun delete(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int {
        return recipeDao.delete(recipeDTO)
    }

    override suspend fun getAll(): List<br.com.aldemir.data.database.model.RecipeDTO> {
        return recipeDao.getAll()
    }
}