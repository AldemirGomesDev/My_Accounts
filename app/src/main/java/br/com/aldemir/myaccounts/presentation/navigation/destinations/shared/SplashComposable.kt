package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.splash.SplashScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.util.Const.NavigationAnimationDurationMillis
import com.google.accompanist.navigation.animation.composable

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