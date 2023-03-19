package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.home.HomeScreen
import br.com.aldemir.myaccounts.presentation.home.state.ButtonType
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.util.Const.NavigationAnimationDurationMillis
import com.google.accompanist.navigation.animation.composable


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(
    navHostController: NavHostController,
) {
    composable(
        route = Route.Home.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
    ) {
        HomeScreen(
            navigateToNextScreen = { type ->
                when(type) {
                    ButtonType.ButtonRecipe -> {
                        navHostController.navigate(
                            Route.ListRecipe.route
                        )
                    }
                    ButtonType.ButtonExpense -> {
                        navHostController.navigate(
                            Route.ExpenseList.route
                        )
                    }
                }
            },
        )
    }
}