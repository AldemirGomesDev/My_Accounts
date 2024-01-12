package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class GetAllRecipeUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
) : GetAllRecipeUseCase {
    override suspend fun invoke(): List<RecipeDomain> {
        return recipeRepository.getAll()
    }
}