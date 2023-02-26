package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.Recipe

interface AddRecipeUseCase {
    suspend operator fun invoke(recipe: Recipe): Long
}