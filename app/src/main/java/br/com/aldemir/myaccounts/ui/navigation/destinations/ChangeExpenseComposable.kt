package br.com.aldemir.myaccounts.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.aldemir.myaccounts.ui.expensechange.ChangeExpenseScreen
import br.com.aldemir.myaccounts.ui.navigation.Route
import br.com.aldemir.myaccounts.util.Const
import br.com.aldemir.myaccounts.util.emptyString
import com.google.accompanist.navigation.animation.composable

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.changeExpenseComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.ExpenseChange.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 1000
                )
            )
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1500 }, animationSpec = tween(1500))
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 1000)
            )
        },
        arguments = listOf(
            navArgument(Const.EXPENSE_ID) {
                type = NavType.IntType
            },
            navArgument(Const.EXPENSE_NAME) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val idMonthlyPayment = backStackEntry.arguments?.getInt(Const.EXPENSE_ID)
        val expenseName = backStackEntry.arguments?.getString(Const.EXPENSE_NAME)
        ChangeExpenseScreen(
            idMonthlyPayment = idMonthlyPayment ?: 0,
            expenseName = expenseName ?: emptyString(),
            navigateToDetailScreen = {
                navHostController.navigateUp()
            },
        )
    }
}