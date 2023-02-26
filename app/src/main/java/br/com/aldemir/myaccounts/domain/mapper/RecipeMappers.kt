package br.com.aldemir.myaccounts.domain.mapper

import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeView


fun RecipePerMonth.toRecipeView(expired: Boolean) = RecipeView(
    id = id_recipe,
    name = name,
    description = description,
    due_date = due_date,
    status = status,
    expired = expired
)

fun RecipeView.toDatabase() = Recipe(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)