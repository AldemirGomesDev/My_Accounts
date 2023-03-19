package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import javax.inject.Inject

class GetAllRecipeMonthUseCaseImpl @Inject constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
): GetAllRecipeMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<RecipeMonthlyDTO> {
        return recipePerMonthlyRepository.getAllRecipeMonth(month, year)
    }
}