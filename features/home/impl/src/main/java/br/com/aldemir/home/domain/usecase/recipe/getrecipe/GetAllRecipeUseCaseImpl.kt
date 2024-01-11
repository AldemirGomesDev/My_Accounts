package br.com.aldemir.home.domain.usecase.recipe.getrecipe

import br.com.aldemir.publ.domain.recipe.GetAllRecipeUseCase
import br.com.aldemir.data.repository.recipe.RecipeRepository

class GetAllRecipeUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
) : GetAllRecipeUseCase {
    override suspend fun invoke(): List<br.com.aldemir.data.database.model.RecipeDTO> {
        return recipeRepository.getAll()
    }
}