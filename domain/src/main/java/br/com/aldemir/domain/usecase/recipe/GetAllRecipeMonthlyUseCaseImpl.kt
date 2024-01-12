package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : GetAllRecipeMonthlyUseCase {
    override suspend fun invoke(month: String, year: String): List<RecipeMonthlyDomain> {
        return recipeMonthlyRepository.getAllRecipeMonth(month, year)
    }
}