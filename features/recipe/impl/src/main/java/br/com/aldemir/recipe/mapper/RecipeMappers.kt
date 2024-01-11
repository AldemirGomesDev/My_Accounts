package br.com.aldemir.recipe.mapper

import br.com.aldemir.data.database.model.RecipeDTO
import br.com.aldemir.data.database.model.RecipeMonthlyDTO
import br.com.aldemir.data.database.model.RecipeMonthlyDomain
import br.com.aldemir.data.database.model.RecipePerMonthDTO
import br.com.aldemir.recipe.model.RecipeMonthlyView
import br.com.aldemir.recipe.model.RecipeView

fun RecipePerMonthDTO.toDatabase() =
    RecipeMonthlyDTO(
        id = id,
        id_recipe = id_recipe,
        year = year,
        month = month,
        value = value,
        status = status,
    )

fun RecipeMonthlyView.viewToDatabase() = RecipeMonthlyDTO(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    status = status,
)

fun RecipeMonthlyDomain.toView(expired: Boolean) = RecipeMonthlyView(
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

fun RecipePerMonthDTO.toRecipeView(expired: Boolean) = RecipeView(
    id = id_recipe,
    name = name,
    description = description,
    due_date = due_date,
    status = status,
    expired = expired
)

fun RecipeView.toDatabase() = RecipeDTO(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)