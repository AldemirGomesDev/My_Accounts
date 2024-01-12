package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeDomain

interface GetAllRecipeUseCase {
    suspend operator fun invoke(): List<RecipeDomain>
}