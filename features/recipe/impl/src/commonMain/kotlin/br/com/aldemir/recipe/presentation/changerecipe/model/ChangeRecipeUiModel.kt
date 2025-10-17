package br.com.aldemir.recipe.presentation.changerecipe.model

import br.com.aldemir.common.util.emptyString

data class ChangeRecipeUiModel(
    val recipeId: Int = 0,
    val value: Double = 0.0,
    val isValueValid: Boolean = false,
    val valueError: String = emptyString(),
    val name: String = emptyString(),
    val isNameValid: Boolean = false,
    val nameError: String = emptyString(),
    val description: String = emptyString(),
    val isDescriptionValid: Boolean = false,
    val descriptionError: String = emptyString(),
    val isCheckedPaid: Boolean = false,
    var isEnabledRegisterButton: Boolean = true,
    val idMonthlyRecipe: Int = 0,
    var year: String = emptyString(),
    var month: String = emptyString(),
)
