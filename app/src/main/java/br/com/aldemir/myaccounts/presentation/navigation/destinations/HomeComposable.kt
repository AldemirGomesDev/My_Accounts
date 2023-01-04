package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.main.HomeScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import com.google.accompanist.navigation.animation.composable


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.Home.route,
        enterTransition = {
            slideInVertically(initialOffsetY = { 1000 }, animationSpec = tween(500))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
        },
    ) {
        HomeScreen(
            navigateToTaskScreen = { expenseId, expenseName ->
                navHostController.navigate(
                    Route.ExpenseDetail.createRoute(expenseId, expenseName)
                )
            },
            navigateToAddScreen = {
                navHostController.navigate(
                    Route.ExpenseAdd.route
                )
            }
        )
    }
}
