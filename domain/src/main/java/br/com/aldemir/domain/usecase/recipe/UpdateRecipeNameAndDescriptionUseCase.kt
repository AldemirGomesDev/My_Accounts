package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeUpdateDomain


interface UpdateRecipeNameAndDescriptionUseCase {
    suspend operator fun invoke(recipeUpdateDomain: RecipeUpdateDomain): Int
}