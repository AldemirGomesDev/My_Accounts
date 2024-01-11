package br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.publ.domain.recipe.GetAllRecipePerMonthUseCase
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class GetAllRecipePerMonthUseCaseImpl constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
) : GetAllRecipePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<br.com.aldemir.data.database.model.RecipePerMonthDTO> {
        return recipePerMonthlyRepository.getAllRecipePerMonth(month, year)
    }
}