package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllRecipePerMonthUseCaseImpl constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
) : GetAllRecipePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<RecipePerMonthDomain> {
        return recipePerMonthlyRepository.getAllRecipePerMonth(month, year)
    }
}