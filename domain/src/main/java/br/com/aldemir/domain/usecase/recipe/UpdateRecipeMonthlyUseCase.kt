package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain

interface UpdateRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthlyDomain: RecipeMonthlyDomain): Int
}