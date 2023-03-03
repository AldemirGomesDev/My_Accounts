package br.com.aldemir.myaccounts.domain.usecase.recipe.delete

import br.com.aldemir.myaccounts.data.model.Recipe


interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipe: Recipe): Int
}