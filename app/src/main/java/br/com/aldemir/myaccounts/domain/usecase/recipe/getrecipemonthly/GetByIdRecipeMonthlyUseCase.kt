package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly

import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO

interface GetByIdRecipeMonthlyUseCase {
    suspend operator fun invoke(id: Int): RecipePerMonthDTO
}