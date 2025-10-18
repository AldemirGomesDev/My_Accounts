package br.com.aldemir.recipe.presentation.addrecipe.effect

sealed class AddRecipeEffect {
    data object RecipeSaved : AddRecipeEffect()
}