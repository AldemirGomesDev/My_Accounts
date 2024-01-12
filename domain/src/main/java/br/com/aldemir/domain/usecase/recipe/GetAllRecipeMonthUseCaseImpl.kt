package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllRecipeMonthUseCaseImpl constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
): GetAllRecipeMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<RecipeMonthlyDomain> {
        return recipePerMonthlyRepository.getAllRecipeMonth(month, year)
    }
}