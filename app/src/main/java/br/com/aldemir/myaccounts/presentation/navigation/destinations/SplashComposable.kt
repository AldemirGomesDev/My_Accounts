package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.splash.SplashScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.Splash.route,
        enterTransition = {
            slideInVertically(initialOffsetY = { 1000 }, animationSpec = tween(500))
        },
        exitTransition = {
            slideOutVertically(targetOffsetY = { -1000 }, animationSpec = tween(500))
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