package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipePerMonthDomain

interface GetByIdRecipeMonthlyUseCase {
    suspend operator fun invoke(id: Int): RecipePerMonthDomain
}