package br.com.aldemir.navigation.destinations.shared

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.common.util.Const
import br.com.aldemir.home.presentation.view.HomeScreen
import br.com.aldemir.home.presentation.model.ButtonType
import br.com.aldemir.navigation.Route
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(
    navHostController: NavHostController,
) {
    composable(
        route = Route.Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(Const.NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(Const.NavigationAnimationDurationMillis)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(Const.NavigationAnimationDurationMillis)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(Const.NavigationAnimationDurationMillis)
            )
        },
    ) {
        HomeScreen(
            navigateToNextScreen = { type ->
                when (type) {
                    ButtonType.ButtonRecipe -> {
                        navHostController.navigate(
                            Route.ListRecipe.route
                        )
                    }

                    ButtonType.ButtonExpense -> {
                        navHostController.navigate(
                            Route.ExpenseGraphRoute.route
                        )
                    }
                }
            },
        )
    }
}