package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class UpdateRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UpdateRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthlyDomain: RecipeMonthlyDomain): Int {
        return recipeMonthlyRepository.update(recipeMonthlyDomain)
    }
}