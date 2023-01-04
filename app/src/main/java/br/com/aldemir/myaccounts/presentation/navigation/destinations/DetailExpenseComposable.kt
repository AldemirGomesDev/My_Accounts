package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.aldemir.myaccounts.presentation.expensedetail.ExpenseDetailScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.util.Const
import br.com.aldemir.myaccounts.util.emptyString
import com.google.accompanist.navigation.animation.composable

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.detailExpenseComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.ExpenseDetail.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
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
        val expenseId = backStackEntry.arguments?.getInt(Const.EXPENSE_ID)
        val expenseName = backStackEntry.arguments?.getString(Const.EXPENSE_NAME)
        ExpenseDetailScreen(
            expenseId = expenseId ?: 0,
            expenseName = expenseName ?: emptyString(),
            navigateToChangeScreen = { idMonthlyPayment ->
                navHostController.navigate(
                    Route.ExpenseChange.createRoute(
                        idMonthlyPayment,
                        expenseName ?: emptyString()
                    )
                )
            },
            navigateToBackScreen = { navHostController.navigateUp() }
        )
    }
}