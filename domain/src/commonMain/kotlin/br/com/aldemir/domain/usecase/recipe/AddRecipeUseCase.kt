package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class AddRecipeUseCase constructor(
    private val recipeRepository: RecipeRepository
) : BaseUseCase<RecipeDomain, Long> {

    override suspend fun execute(params: RecipeDomain): Long {
        return recipeRepository.insert(params)
    }
}