package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipePerMonthDomain

interface GetAllByIdRecipeUseCase {
    suspend operator fun invoke(id: Int): List<RecipePerMonthDomain>
}