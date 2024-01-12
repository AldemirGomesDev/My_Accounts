package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class DeleteRecipeUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
): DeleteRecipeUseCase {
    override suspend fun invoke(recipeDomain: RecipeDomain): Int {
       return recipeRepository.delete(recipeDomain)
    }
}