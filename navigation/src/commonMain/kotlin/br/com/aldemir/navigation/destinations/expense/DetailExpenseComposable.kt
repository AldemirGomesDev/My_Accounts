package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.aldemir.expense.presentation.expensedetail.ExpenseDetailScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.detailExpenseComposable(
    navHostController: NavHostController
) {
    composable<Routes.ExpenseGraphRoute.ExpenseDetail>(
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
    ) { backStackEntry ->
        val expenseId = backStackEntry.toRoute<Routes.ExpenseGraphRoute.ExpenseDetail>().expenseId
        val expenseName = backStackEntry.toRoute<Routes.ExpenseGraphRoute.ExpenseDetail>().expenseName
        ExpenseDetailScreen(
            expenseId = expenseId,
            expenseName = expenseName,
            navigateToChangeScreen = { idMonthlyPayment ->
                navHostController.navigate(
                    Routes.ExpenseGraphRoute.ExpenseChange(
                        idMonthlyPayment,
                        expenseName
                    )
                )
            },
            navigateToBackScreen = { navHostController.navigateUp() }
        )
    }
}