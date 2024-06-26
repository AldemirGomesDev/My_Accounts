package br.com.aldemir.navigation

import br.com.aldemir.common.util.Const.EXPENSE_ADD_SCREEN
import br.com.aldemir.common.util.Const.EXPENSE_CHANGE_SCREEN
import br.com.aldemir.common.util.Const.EXPENSE_ID
import br.com.aldemir.common.util.Const.EXPENSE_LIST_SCREEN
import br.com.aldemir.common.util.Const.EXPENSE_NAME
import br.com.aldemir.common.util.Const.HOME_SCREEN
import br.com.aldemir.common.util.Const.SPLASH_SCREEN
import br.com.aldemir.common.util.Const.EXPENSE_SCREEN
import br.com.aldemir.common.util.Const.HISTORIC_SCREEN
import br.com.aldemir.common.util.Const.RECIPE_CHANGE_SCREEN
import br.com.aldemir.common.util.Const.RECIPE_DETAIL_SCREEN
import br.com.aldemir.common.util.Const.RECIPE_ID
import br.com.aldemir.common.util.Const.RECIPE_LIST_SCREEN
import br.com.aldemir.common.util.Const.RECIPE_SCREEN

sealed class Route(val route: String) {
    object Splash: Route(SPLASH_SCREEN)
    object Home: Route(HOME_SCREEN)
    object ExpenseList: Route(EXPENSE_LIST_SCREEN)
    object Historic: Route(HISTORIC_SCREEN)
    object ExpenseAdd: Route(EXPENSE_ADD_SCREEN)
    object ExpenseDetail: Route("$EXPENSE_SCREEN/{$EXPENSE_ID}/{$EXPENSE_NAME}") {
        fun createRoute(expenseId: Int, expenseName: String) = "$EXPENSE_SCREEN/$expenseId/$expenseName"
    }
    object ExpenseChange: Route("$EXPENSE_CHANGE_SCREEN/{$EXPENSE_ID}/{$EXPENSE_NAME}") {
        fun createRoute(idMonthlyPayment: Int, expenseName: String) = "$EXPENSE_CHANGE_SCREEN/$idMonthlyPayment/$expenseName"
    }
    object AddRecipe: Route(RECIPE_SCREEN)
    object ListRecipe: Route(RECIPE_LIST_SCREEN)
    object DetailRecipe: Route("$RECIPE_DETAIL_SCREEN/{$RECIPE_ID}") {
        fun createRoute(recipeId: Int) = "$RECIPE_DETAIL_SCREEN/$recipeId"
    }
    object ChangeRecipe: Route("$RECIPE_CHANGE_SCREEN/{$RECIPE_ID}") {
        fun createRoute(idMonthlyRecipe: Int) = "$RECIPE_CHANGE_SCREEN/$idMonthlyRecipe"
    }
}
