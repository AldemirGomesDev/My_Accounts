package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.domain.model.RecipePerMonth

interface GetByIdRecipeMonthlyUseCase {
    suspend operator fun invoke(id: Int): RecipePerMonth
}