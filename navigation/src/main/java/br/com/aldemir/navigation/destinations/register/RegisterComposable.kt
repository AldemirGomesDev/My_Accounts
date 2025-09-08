package br.com.aldemir.navigation.destinations.register

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.navigation.Route
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.register.presentation.RegisterScreen

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.registerComposable(
    isDarkTheme: Boolean,
    navHostController: NavHostController,
) {
    composable(
        route = Route.Register.route,
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
        RegisterScreen(
            isDarkTheme = isDarkTheme,
            navigateToLoginScreen = {
                navHostController.navigate(Route.Authentication.route) {
                    popUpTo(Route.Authentication.route) {
                        inclusive = true
                    }
                }
            },
        )
    }
}