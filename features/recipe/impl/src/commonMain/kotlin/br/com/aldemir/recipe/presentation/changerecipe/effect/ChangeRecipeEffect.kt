package br.com.aldemir.recipe.presentation.changerecipe.effect

sealed class ChangeRecipeEffect {
    object NavigateToRecipeList : ChangeRecipeEffect()
}