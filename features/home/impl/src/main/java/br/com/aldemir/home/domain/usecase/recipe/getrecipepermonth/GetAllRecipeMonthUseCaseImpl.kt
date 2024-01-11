package br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class GetAllRecipeMonthUseCaseImpl constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
): GetAllRecipeMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO> {
        return recipePerMonthlyRepository.getAllRecipeMonth(month, year)
    }
}