package br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.data.database.model.RecipeMonthlyDTO

interface GetAllRecipeMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<br.com.aldemir.data.database.model.RecipeMonthlyDTO>
}