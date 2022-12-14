package br.com.aldemir.myaccounts.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.ui.SplashScreen
import br.com.aldemir.myaccounts.ui.navigation.Route
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.Splash.route,
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 1000)
            )
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