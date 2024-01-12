package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.usecase.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllByIdRecipeUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : GetAllByIdRecipeUseCase {
    override suspend fun invoke(id: Int): List<RecipePerMonthDomain> {
        return recipeMonthlyRepository.getAllByIdRecipe(id)
    }
}