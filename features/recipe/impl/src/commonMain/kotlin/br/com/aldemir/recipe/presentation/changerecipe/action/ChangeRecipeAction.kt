package br.com.aldemir.recipe.presentation.changerecipe.action

sealed class ChangeRecipeAction {
    data class UpdateMonthlyRecipe(val id: Int, val isPaid: Boolean) : ChangeRecipeAction()
    data class OnTitleChange(val title: String) : ChangeRecipeAction()
    data class OnValueChange(val value: String) : ChangeRecipeAction()
    data class OnDescriptionChange(val description: String) : ChangeRecipeAction()
    data class OnCheckedChange(val isCheckedPaid: Boolean) : ChangeRecipeAction()
}