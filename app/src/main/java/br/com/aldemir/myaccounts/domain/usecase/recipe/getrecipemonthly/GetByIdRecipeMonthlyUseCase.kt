package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.model.RecipeMonthly

interface GetByIdRecipeMonthlyUseCase {
    suspend operator fun invoke(id: Int): RecipeMonthly
}