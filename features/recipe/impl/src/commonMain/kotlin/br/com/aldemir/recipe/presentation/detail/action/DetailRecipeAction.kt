package br.com.aldemir.recipe.presentation.detail.action

sealed class DetailRecipeAction {
    data class FetchData(val id: Int) : DetailRecipeAction()
    data class UpdateRecipeMonthly(val id: Int) : DetailRecipeAction()
    data class UpdateShowDialog(val showDialog: Boolean) : DetailRecipeAction()
}