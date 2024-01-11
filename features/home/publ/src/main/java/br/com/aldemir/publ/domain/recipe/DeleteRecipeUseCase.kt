package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeDTO


interface DeleteRecipeUseCase {
    suspend operator fun invoke(recipeDTO: RecipeDTO): Int
}