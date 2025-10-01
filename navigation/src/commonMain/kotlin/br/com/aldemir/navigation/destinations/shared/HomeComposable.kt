package br.com.aldemir.navigation.destinations.shared

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.common.PlatformActivity
import br.com.aldemir.home.presentation.view.HomeScreen
import br.com.aldemir.home.presentation.model.ButtonType
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi

fun NavGraphBuilder.homeComposable(
    navHostController: NavHostController,
) {
    composable<Routes.Home>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
    ) {
        HomeScreen(
            navigateToNextScreen = { type ->
                when (type) {
                    ButtonType.ButtonRecipe -> {
                        navHostController.navigate(
                            Routes.ListRecipe
                        )
                    }

                    ButtonType.ButtonExpense -> {
                        navHostController.navigate(
                            Routes.ExpenseGraphRoute.ExpenseList
                        )
                    }
                }
            },
            onFinish = {
                PlatformActivity.moveAppToBackground()
            }
        )
    }
}