package br.com.aldemir.myaccounts.domain.usecase.recipe.update

import br.com.aldemir.myaccounts.data.model.RecipeMonthly

interface UpdateRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthly: RecipeMonthly): Int
}