package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import javax.inject.Inject

class AddRecipeMonthlyUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : AddRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthly: RecipeMonthly): Long {
        return recipeMonthlyRepository.insert(recipeMonthly)
    }
}