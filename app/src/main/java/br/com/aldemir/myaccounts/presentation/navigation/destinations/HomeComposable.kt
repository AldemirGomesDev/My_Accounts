package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.main.HomeScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import com.google.accompanist.navigation.animation.composable


@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.Home.route,
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1500 }, animationSpec = tween(1500))
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
