package br.com.aldemir.myaccounts.domain.mapper

import br.com.aldemir.myaccounts.data.model.RecipeDTO
import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeMonthlyView
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeView


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

fun RecipeMonthlyView.toDatabase() = RecipeMonthlyDTO(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    status = status,
)

fun RecipePerMonthDTO.toDatabase() = RecipeMonthlyDTO(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    status = status,
)