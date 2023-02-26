package br.com.aldemir.myaccounts.domain.mapper

import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.model.RecipeMonthlyDomain
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import br.com.aldemir.myaccounts.presentation.shared.model.MonthlyPaymentView
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeMonthlyView
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

fun RecipeMonthlyDomain.toView(expired: Boolean) = RecipeMonthlyView(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    due_date = due_date,
    status = status,
    expired = expired
)

fun RecipeMonthlyView.toDatabase() = RecipeMonthly(
    id = id,
    id_recipe = id_recipe,
    year = year,
    month = month,
    value = value,
    status = status,
)