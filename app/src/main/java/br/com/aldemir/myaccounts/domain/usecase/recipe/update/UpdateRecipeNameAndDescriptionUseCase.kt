package br.com.aldemir.myaccounts.domain.usecase.recipe.update

import br.com.aldemir.myaccounts.data.model.RecipeUpdateDTO


interface UpdateRecipeNameAndDescriptionUseCase {
    suspend operator fun invoke(recipeUpdateDTO: RecipeUpdateDTO): Int
}