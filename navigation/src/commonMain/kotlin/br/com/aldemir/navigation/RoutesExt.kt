package br.com.aldemir.navigation

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Routes.Home.toNavRoute(): String =
    Json.encodeToString(this)

fun Routes.ExpenseGraphRoute.ExpenseList.toNavRoute(): String =
    Json.encodeToString(this)

fun Routes.ListRecipe.toNavRoute(): String =
    Json.encodeToString(this)

fun String.toRoutes(): Routes =
    Json.decodeFromString(this)