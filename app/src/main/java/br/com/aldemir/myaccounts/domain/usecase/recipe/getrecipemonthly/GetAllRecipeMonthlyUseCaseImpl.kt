package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import javax.inject.Inject

class GetAllRecipeMonthlyUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : GetAllRecipeMonthlyUseCase {
    override suspend fun invoke(month: String, year: String): List<RecipeMonthlyDTO> {
        return recipeMonthlyRepository.getAllRecipeMonth(month, year)
    }
}