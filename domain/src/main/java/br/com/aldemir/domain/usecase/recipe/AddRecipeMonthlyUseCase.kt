package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain

interface AddRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthlyDomain: RecipeMonthlyDomain): Long
}