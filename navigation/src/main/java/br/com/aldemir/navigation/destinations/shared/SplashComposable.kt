package br.com.aldemir.navigation.destinations.shared

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.common.presentation.splash.SplashScreen
import br.com.aldemir.navigation.Route
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.Splash.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
    ) {
        SplashScreen(
            navigateToListScreen = {
                navHostController.navigate(Route.Home.route) {
                    popUpTo(Route.Splash.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}