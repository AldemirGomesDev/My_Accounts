package br.com.aldemir.recipe.presentation.detail.model

import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.recipe.model.RecipeMonthlyView

data class DetailRecipeUiModel(
    val recipeId: Int = 0,
    val recipesMonthlyView: List<RecipeMonthlyView> = emptyList(),
    val menuItems: List<DropdownItemState> = emptyList(),
    val name: String = emptyString(),
    val showDialog: Boolean = false
)
