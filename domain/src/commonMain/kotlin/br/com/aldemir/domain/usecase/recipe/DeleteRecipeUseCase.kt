package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class DeleteRecipeUseCase(
    private val recipeRepository: RecipeRepository
): UseCase<RecipeDomain, Int> {

    override suspend fun execute(params: RecipeDomain): Int {
        return recipeRepository.delete(params)
    }
}