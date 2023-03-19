package br.com.aldemir.myaccounts.domain.usecase.recipe.update

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import javax.inject.Inject

class UpdateRecipeMonthlyUseCaseImpl @Inject constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UpdateRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthlyDTO: RecipeMonthlyDTO): Int {
        return recipeMonthlyRepository.update(recipeMonthlyDTO)
    }
}