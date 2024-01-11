package br.com.aldemir.home.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.publ.domain.recipe.GetAllRecipeMonthlyUseCase
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class GetAllRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : GetAllRecipeMonthlyUseCase {
    override suspend fun invoke(month: String, year: String): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO> {
        return recipeMonthlyRepository.getAllRecipeMonth(month, year)
    }
}