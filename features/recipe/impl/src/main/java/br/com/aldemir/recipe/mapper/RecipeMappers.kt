package br.com.aldemir.recipe.mapper

import br.com.aldemir.data.database.model.RecipeMonthlyDTO
import br.com.aldemir.data.database.model.RecipePerMonthDTO
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.recipe.model.RecipeMonthlyView
import br.com.aldemir.recipe.model.RecipeView

fun RecipePerMonthDomain.toDatabase() =
    RecipeMonthlyDomain(
        id = id,
        id_recipe = id_recipe,
        year = year,
        month = month,
        value = value,
        status = status,
    )

fun RecipeMonthlyView.viewToDomain() = RecipeMonthlyDomain(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    status = status,
)

fun RecipePerMonthDomain.toRecipeView(expired: Boolean) = RecipeView(
    id = id_recipe,
    name = name,
    description = description,
    due_date = due_date,
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


fun RecipeView.toDomain() = RecipeDomain(
    id = id,
    name = name,
    description = description,
    created_at = created_at,
    due_date = due_date,
    status = status
)