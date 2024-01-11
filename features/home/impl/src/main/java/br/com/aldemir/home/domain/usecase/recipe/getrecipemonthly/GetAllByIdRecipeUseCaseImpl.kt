package br.com.aldemir.home.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.publ.domain.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class GetAllByIdRecipeUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : GetAllByIdRecipeUseCase {
    override suspend fun invoke(id: Int): List<br.com.aldemir.data.database.model.RecipeMonthlyDomain> {
        return recipeMonthlyRepository.getAllByIdRecipe(id)
    }
}