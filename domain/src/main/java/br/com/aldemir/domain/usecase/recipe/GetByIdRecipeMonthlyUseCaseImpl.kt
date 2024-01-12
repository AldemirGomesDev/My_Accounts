package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetByIdRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): GetByIdRecipeMonthlyUseCase {
    override suspend fun invoke(id: Int): RecipePerMonthDomain {
        return recipeMonthlyRepository.getByIdRecipeMonthly(id)
    }
}