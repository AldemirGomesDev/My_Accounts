package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeUpdateDTO


interface UpdateRecipeNameAndDescriptionUseCase {
    suspend operator fun invoke(recipeUpdateDTO: RecipeUpdateDTO): Int
}