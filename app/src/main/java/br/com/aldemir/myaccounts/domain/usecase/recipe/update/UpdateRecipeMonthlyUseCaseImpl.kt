package br.com.aldemir.myaccounts.domain.usecase.recipe.update

import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import javax.inject.Inject

class UpdateRecipeMonthlyUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UpdateRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthly: RecipeMonthly): Int {
        return recipeMonthlyRepository.update(recipeMonthly)
    }
}