package br.com.aldemir.myaccounts.domain.usecase.recipe.delete

import br.com.aldemir.myaccounts.data.model.RecipeDTO


interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipeDTO: RecipeDTO): Int
}