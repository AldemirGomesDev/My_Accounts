package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.myaccounts.data.model.RecipeMonthly

interface GetAllRecipeMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipeMonthly>
}