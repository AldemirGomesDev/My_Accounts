package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipePerMonthDomain

interface GetAllRecipePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipePerMonthDomain>
}