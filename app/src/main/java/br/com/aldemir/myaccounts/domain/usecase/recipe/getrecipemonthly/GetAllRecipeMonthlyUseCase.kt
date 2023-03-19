package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO

interface GetAllRecipeMonthlyUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipeMonthlyDTO>
}