package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain

interface GetAllByIdRecipeUseCase {
    suspend operator fun invoke(id: Int): List<RecipeMonthlyDomain>
}