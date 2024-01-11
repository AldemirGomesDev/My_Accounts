package br.com.aldemir.home.domain.usecase.recipe.update

import br.com.aldemir.publ.domain.recipe.UpdateRecipeMonthlyUseCase
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class UpdateRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UpdateRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Int {
        return recipeMonthlyRepository.update(recipeMonthlyDTO)
    }
}