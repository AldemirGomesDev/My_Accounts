package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.expense.listexpense.ListExpenseScreen
import br.com.aldemir.myaccounts.presentation.expense.listexpense.ListExpenseViewModel
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.util.Const.NavigationAnimationDurationMillis
import com.google.accompanist.navigation.animation.composable


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.listExpenseComposable(
    navHostController: NavHostController,
    viewModel: ListExpenseViewModel
) {
    composable(
        route = Route.ExpenseList.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
    ) {
        ListExpenseScreen(
            navigateToTaskScreen = { expenseId, expenseName ->
                navHostController.navigate(
                    Route.ExpenseDetail.createRoute(expenseId, expenseName)
                )
            },
            navigateToAddScreen = {
                navHostController.navigate(
                    Route.ExpenseAdd.route
                )
            },
            navigateToHomeScreen = {
                navHostController.navigate(
                    Route.Home.route
                )
            },
            viewModel = viewModel
        )
    }
}
