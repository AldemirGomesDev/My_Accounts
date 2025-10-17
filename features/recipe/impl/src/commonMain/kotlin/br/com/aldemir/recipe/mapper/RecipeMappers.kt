package br.com.aldemir.recipe.mapper

import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.recipe.model.RecipeMonthlyView
import br.com.aldemir.recipe.model.RecipeView
import br.com.aldemir.recipe.presentation.changerecipe.model.ChangeRecipeUiModel

fun RecipePerMonthDomain.toRecipeView(expired: Boolean) = RecipeView(
    id = id_recipe,
    name = name,
    description = description,
    due_date = due_date,
    value = value,
    status = status,
    expired = expired
)

fun RecipePerMonthDomain.toView(expired: Boolean) = RecipeMonthlyView(
    id = id,
    id_recipe = id_recipe,
    name = name,
    year = year,
    month = month,
    value = value,
    due_date = due_date,
    status = status,
    expired = expired
)

fun RecipePerMonthDomain.toUiModel() = ChangeRecipeUiModel(
    recipeId = id_recipe,
    value = value,
    isValueValid = true,
    name = name,
    isNameValid = true,
    description = description,
    isDescriptionValid = true,
    isCheckedPaid = status,
    idMonthlyRecipe = id,
    year = year,
    month = month,
)


fun RecipeView.toDomain() = RecipeDomain(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)