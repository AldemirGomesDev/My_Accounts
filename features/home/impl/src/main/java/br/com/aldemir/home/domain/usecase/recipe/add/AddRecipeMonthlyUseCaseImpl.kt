package br.com.aldemir.home.domain.usecase.recipe.add

import br.com.aldemir.publ.domain.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository

class AddRecipeMonthlyUseCaseImpl constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : br.com.aldemir.publ.domain.recipe.AddRecipeMonthlyUseCase {
    override suspend fun invoke(recipeMonthlyDTO: br.com.aldemir.data.database.model.RecipeMonthlyDTO): Long {
        return recipeMonthlyRepository.insert(recipeMonthlyDTO)
    }
}