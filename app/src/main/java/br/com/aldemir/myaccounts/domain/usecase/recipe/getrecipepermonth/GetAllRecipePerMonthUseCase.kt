package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth

import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO

interface GetAllRecipePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipePerMonthDTO>
}