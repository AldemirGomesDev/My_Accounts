package br.com.aldemir.myaccounts.ui.navigation

import br.com.aldemir.myaccounts.util.Const.EXPENSE_ADD_SCREEN
import br.com.aldemir.myaccounts.util.Const.EXPENSE_CHANGE_SCREEN
import br.com.aldemir.myaccounts.util.Const.EXPENSE_ID
import br.com.aldemir.myaccounts.util.Const.EXPENSE_NAME
import br.com.aldemir.myaccounts.util.Const.HOME_SCREEN
import br.com.aldemir.myaccounts.util.Const.SPLASH_SCREEN
import br.com.aldemir.myaccounts.util.Const.EXPENSE_SCREEN

sealed class Route(val route: String) {
    object Splash: Route(SPLASH_SCREEN)
    object Home: Route(HOME_SCREEN)
    object ExpenseAdd: Route(EXPENSE_ADD_SCREEN)
    object ExpenseDetail: Route("$EXPENSE_SCREEN/{$EXPENSE_ID}/{$EXPENSE_NAME}") {
        fun createRoute(expenseId: Int, expenseName: String) = "$EXPENSE_SCREEN/$expenseId/$expenseName"
    }
    object ExpenseChange: Route("$EXPENSE_CHANGE_SCREEN/{$EXPENSE_ID}/{$EXPENSE_NAME}") {
        fun createRoute(idMonthlyPayment: Int, expenseName: String) = "$EXPENSE_CHANGE_SCREEN/$idMonthlyPayment/$expenseName"
    }
}
