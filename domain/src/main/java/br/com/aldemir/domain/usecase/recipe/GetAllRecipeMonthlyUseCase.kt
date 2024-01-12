package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeMonthlyDomain

interface GetAllRecipeMonthlyUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipeMonthlyDomain>
}