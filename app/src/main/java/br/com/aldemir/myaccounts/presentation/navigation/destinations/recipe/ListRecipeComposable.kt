package br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.recipe.presentation.list.ListRecipeScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.listRecipeComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.ListRecipe.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
        },
    ) {
        ListRecipeScreen(
            navigateToDetailScreen = { recipeId ->
                navHostController.navigate(
                    Route.DetailRecipe.createRoute(recipeId)
                )
            },
            navigateToHomeScreen = {
                navHostController.navigate(
                    Route.Home.route
                )
            },
            navigateToAddRecipeScreen = {
                navHostController.navigate(
                    Route.AddRecipe.route
                )
            },
        )
    }
}