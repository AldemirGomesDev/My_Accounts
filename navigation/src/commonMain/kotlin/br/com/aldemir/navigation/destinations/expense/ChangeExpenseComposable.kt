package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.expense.presentation.expensechange.ChangeExpenseScreen
import br.com.aldemir.navigation.Routes

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.changeExpenseComposable(
    navHostController: NavHostController
) {
    composable<Routes.ExpenseGraphRoute.ExpenseChange>(
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
    ) { backStackEntry ->
        val idMonthlyPayment = backStackEntry.toRoute<Routes.ExpenseGraphRoute.ExpenseChange>().idMonthlyPayment
        val expenseName = backStackEntry.toRoute<Routes.ExpenseGraphRoute.ExpenseChange>().expenseName
        ChangeExpenseScreen(
            idMonthlyPayment = idMonthlyPayment,
            expenseName = expenseName,
            navigateToDetailScreen = {
                navHostController.navigateUp()
            },
        )
    }
}