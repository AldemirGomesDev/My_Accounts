package br.com.aldemir.recipe.presentation.list.model

import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.recipe.model.RecipeView

data class ListRecipeUiModel(
    val recipes: List<RecipeView> = emptyList(),
    val recipeMonthlyDomain: List<RecipeMonthlyDomain> = emptyList(),
    val cardState: CardState = CardState(),
    val showDialog: Boolean = false,
    val menuItems: List<DropdownItemState> = emptyList()
)
