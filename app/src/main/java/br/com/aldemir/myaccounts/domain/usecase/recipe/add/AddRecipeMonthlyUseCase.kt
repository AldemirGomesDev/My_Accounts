package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.RecipeMonthly

interface AddRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthly: RecipeMonthly): Long
}