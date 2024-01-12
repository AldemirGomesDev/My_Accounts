package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class AddRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : AddRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthlyDomain: RecipeMonthlyDomain): Long {
        return recipeMonthlyRepository.insert(recipeMonthlyDomain)
    }
}