package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class AddRecipeMonthlyUseCase(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : UseCase<RecipeMonthlyDomain, Long> {

    override suspend fun execute(params: RecipeMonthlyDomain): Long {
        return recipeMonthlyRepository.insert(params)
    }
}