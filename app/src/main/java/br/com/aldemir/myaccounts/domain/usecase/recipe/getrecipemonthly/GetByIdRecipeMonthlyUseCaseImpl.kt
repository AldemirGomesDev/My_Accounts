package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO
import javax.inject.Inject

class GetByIdRecipeMonthlyUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): GetByIdRecipeMonthlyUseCase {
    override suspend fun invoke(id: Int): RecipePerMonthDTO {
        return recipeMonthlyRepository.getByIdRecipeMonthly(id)
    }
}