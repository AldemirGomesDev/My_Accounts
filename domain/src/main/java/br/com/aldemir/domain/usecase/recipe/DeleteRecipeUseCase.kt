package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeDomain


interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipeDomain: RecipeDomain): Int
}