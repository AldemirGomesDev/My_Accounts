package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class AddRecipeUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
) : AddRecipeUseCase {
    override suspend fun invoke(recipeDomain: RecipeDomain): Long {
        return recipeRepository.insert(recipeDomain)
    }
}