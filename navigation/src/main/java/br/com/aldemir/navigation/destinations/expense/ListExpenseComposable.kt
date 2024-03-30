package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.expense.presentation.listexpense.ListExpenseScreen
import br.com.aldemir.expense.presentation.listexpense.ListExpenseViewModel
import br.com.aldemir.navigation.Route
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.listExpenseComposable(
    navHostController: NavHostController,
) {
    composable(
        route = Route.ExpenseGraphRoute.ExpenseList.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )        },
    ) {
        ListExpenseScreen(
            navigateToTaskScreen = { expenseId, expenseName ->
                navHostController.navigate(
                    Route.ExpenseGraphRoute.ExpenseDetail.createRoute(expenseId, expenseName)
                )
            },
            navigateToAddScreen = {
                navHostController.navigate(
                    Route.ExpenseGraphRoute.ExpenseAdd.route
                )
            },
            navigateToHomeScreen = {
                navHostController.navigate(Route.Home.route)
            },
        )
    }
}
