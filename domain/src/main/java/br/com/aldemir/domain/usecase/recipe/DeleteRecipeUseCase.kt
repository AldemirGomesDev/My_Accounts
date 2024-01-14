package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class DeleteRecipeUseCase constructor(
    private val recipeRepository: RecipeRepository
): BaseUseCase<RecipeDomain, Int> {

    override suspend fun execute(params: RecipeDomain): Int {
        return recipeRepository.delete(params)
    }
}