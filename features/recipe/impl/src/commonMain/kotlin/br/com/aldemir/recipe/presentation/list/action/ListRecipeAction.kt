package br.com.aldemir.recipe.presentation.list.action

import br.com.aldemir.recipe.model.RecipeView

sealed class ListRecipeAction {
    data object LoadRecipes : ListRecipeAction()
    data class DeleteRecipe(val expense: RecipeView) : ListRecipeAction()
    data class ShowDialog(val showDialog: Boolean) : ListRecipeAction()
}