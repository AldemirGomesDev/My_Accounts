package br.com.aldemir.recipe.presentation.addrecipe.action

sealed class AddRecipeAction {
    data object SaveRecipe : AddRecipeAction()
    data class SetName(val name: String) : AddRecipeAction()
    data class SetValue(val value: String) : AddRecipeAction()
    data class SetDescription(val description: String) : AddRecipeAction()
    data class SetIsCheckedPaid(val isCheckedPaid: Boolean) : AddRecipeAction()
    data class SetIsAccountRepeat(val isAccountRepeat: Boolean) : AddRecipeAction()
    data class SetAmountThatRepeatsSelected(val amountThatRepeatsSelected: Int) : AddRecipeAction()
    data class SetDueDateSelected(val dueDateSelected: Int) : AddRecipeAction()
}