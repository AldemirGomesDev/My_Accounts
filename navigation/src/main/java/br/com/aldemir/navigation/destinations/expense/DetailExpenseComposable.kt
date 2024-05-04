package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.aldemir.expense.presentation.expensedetail.ExpenseDetailScreen
import br.com.aldemir.navigation.Route
import br.com.aldemir.common.util.Const
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.common.util.emptyString

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.detailExpenseComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.ExpenseGraphRoute.ExpenseDetail.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(NavigationAnimationDurationMillis)
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
        val expenseId = backStackEntry.arguments?.getInt(Const.EXPENSE_ID)
        val expenseName = backStackEntry.arguments?.getString(Const.EXPENSE_NAME)
        ExpenseDetailScreen(
            expenseId = expenseId ?: 0,
            expenseName = expenseName ?: emptyString(),
            navigateToChangeScreen = { idMonthlyPayment ->
                navHostController.navigate(
                    Route.ExpenseGraphRoute.ExpenseChange.createRoute(
                        idMonthlyPayment,
                        expenseName ?: emptyString()
                    )
                )
            },
            navigateToBackScreen = { navHostController.navigateUp() }
        )
    }
}