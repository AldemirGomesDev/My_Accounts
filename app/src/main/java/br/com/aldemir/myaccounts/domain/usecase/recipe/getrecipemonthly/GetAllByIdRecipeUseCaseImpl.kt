package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import javax.inject.Inject

class GetAllByIdRecipeUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : GetAllByIdRecipeUseCase {
    override suspend fun invoke(id: Int): List<RecipeMonthlyDomain> {
        return recipeMonthlyRepository.getAllByIdRecipe(id)
    }
}