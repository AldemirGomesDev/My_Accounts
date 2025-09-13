package br.com.aldemir.navigation.destinations.shared

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.common.presentation.splash.SplashScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    isDarkTheme: Boolean,
    navHostController: NavHostController
) {
    composable<Routes.Splash>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
    ) {
        SplashScreen(
            isDarkTheme = isDarkTheme,
            navigateToListScreen  = {
                navHostController.navigate(Routes.Login) {
                    popUpTo(Routes.Splash) {
                        inclusive = true
                    }
                }
            }
        )
    }
}