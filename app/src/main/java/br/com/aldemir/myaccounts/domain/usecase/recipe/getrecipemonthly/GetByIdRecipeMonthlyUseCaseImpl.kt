package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import javax.inject.Inject

class GetByIdRecipeMonthlyUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): GetByIdRecipeMonthlyUseCase {
    override suspend fun invoke(id: Int): RecipePerMonth {
        return recipeMonthlyRepository.getByIdRecipeMonthly(id)
    }
}