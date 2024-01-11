package br.com.aldemir.home.domain.usecase.recipe.add

import br.com.aldemir.publ.domain.recipe.AddRecipeUseCase
import br.com.aldemir.data.repository.recipe.RecipeRepository

class AddRecipeUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
) : AddRecipeUseCase {
    override suspend fun invoke(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Long {
        return recipeRepository.insert(recipeDTO)
    }
}