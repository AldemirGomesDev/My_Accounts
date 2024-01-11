package br.com.aldemir.home.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.publ.domain.recipe.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class GetByIdRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): GetByIdRecipeMonthlyUseCase {
    override suspend fun invoke(id: Int): br.com.aldemir.data.database.model.RecipePerMonthDTO {
        return recipeMonthlyRepository.getByIdRecipeMonthly(id)
    }
}