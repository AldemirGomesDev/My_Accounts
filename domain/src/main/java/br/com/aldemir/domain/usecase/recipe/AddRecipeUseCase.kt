package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeDomain

interface AddRecipeUseCase {
    suspend operator fun invoke(recipeDomain: RecipeDomain): Long
}