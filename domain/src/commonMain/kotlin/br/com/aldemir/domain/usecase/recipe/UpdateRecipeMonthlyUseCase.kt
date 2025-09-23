package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class UpdateRecipeMonthlyUseCase(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UseCase<RecipeMonthlyDomain, Int> {

    override suspend fun execute(params: RecipeMonthlyDomain): Int {
        return recipeMonthlyRepository.update(params)
    }
}