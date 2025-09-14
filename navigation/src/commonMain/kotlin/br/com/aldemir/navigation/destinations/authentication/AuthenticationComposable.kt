package br.com.aldemir.navigation.destinations.authentication

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.authentication.presentation.LoginScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.authenticationComposable(
    isDarkTheme: Boolean,
    navHostController: NavHostController,
) {
    composable<Routes.Login>(
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
    ) {
        LoginScreen(
            isDarkTheme = isDarkTheme,
            navigateToHomeScreen = {
                navHostController.navigate(Routes.Home) {
                    popUpTo(Routes.Login) {
                        inclusive = true
                    }
                }
            },
            navigateToRegisterScreen = {
                navHostController.navigate(Routes.Register) {
                    popUpTo(Routes.Login) {
                        inclusive = false
                    }
                }
            },
        )
    }
}