package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.expense.AddAccountScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import com.google.accompanist.navigation.animation.composable

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addAccountComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.ExpenseAdd.route,
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
            slideOutHorizontally(
                targetOffsetX = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 5000)
            )
        },
    ) {
        AddAccountScreen(
            navigateToHomeScreen = {
                navHostController.navigate(
                    Route.Home.route
                )
            }
        )
    }
}