package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.expense.presentation.listexpense.ListExpenseScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.listExpenseComposable(
    navHostController: NavHostController,
) {
    composable<Routes.ExpenseGraphRoute.ExpenseList>(
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
    ) {
        ListExpenseScreen(
            navigateToTaskScreen = { expenseId, expenseName ->
                navHostController.navigate(
                    Routes.ExpenseGraphRoute.ExpenseDetail(expenseId, expenseName)
                )
            },
            navigateToAddScreen = {
                navHostController.navigate(
                    Routes.ExpenseGraphRoute.ExpenseAdd
                )
            },
            navigateToHomeScreen = {
                navHostController.popBackStack()
            },
        )
    }
}
