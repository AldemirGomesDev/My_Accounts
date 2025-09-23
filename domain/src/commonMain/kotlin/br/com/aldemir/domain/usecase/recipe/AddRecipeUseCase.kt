package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class AddRecipeUseCase(
    private val recipeRepository: RecipeRepository
) : UseCase<RecipeDomain, Long> {

    override suspend fun execute(params: RecipeDomain): Long {
        return recipeRepository.insert(params)
    }
}