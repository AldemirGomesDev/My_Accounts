package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import javax.inject.Inject

class GetAllRecipePerMonthUseCaseImpl @Inject constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
) : GetAllRecipePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<RecipePerMonth> {
        return recipePerMonthlyRepository.getAllRecipePerMonth(month, year)
    }
}