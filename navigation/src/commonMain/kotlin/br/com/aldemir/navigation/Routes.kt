package br.com.aldemir.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Splash
    @Serializable
    data object Login
    @Serializable
    data object Home
    @Serializable
    data object Logout
    @Serializable
    data object Historic
    @Serializable
    data object Register
    @Serializable
    data object AddRecipe
    @Serializable
    data object ListRecipe
    @Serializable
    data class DetailRecipe(val recipeId: Int)
    @Serializable
    data class ChangeRecipe(val idMonthlyRecipe: Int)
    @Serializable
    data object ExpenseGraphRoute {
        @Serializable
        data object ExpenseList

        @Serializable
        data object ExpenseAdd

        @Serializable
        data class ExpenseDetail(val expenseId: Int, val expenseName: String)

        @Serializable
        data class ExpenseChange(val idMonthlyPayment: Int, val expenseName: String)
    }
}