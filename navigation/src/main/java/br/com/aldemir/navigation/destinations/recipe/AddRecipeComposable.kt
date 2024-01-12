package br.com.aldemir.navigation.destinations.recipe

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.navigation.Route
import br.com.aldemir.recipe.presentation.addrecipe.AddRecipeScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addRecipeComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.AddRecipe.route,
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
        AddRecipeScreen(
            navigateToListRecipeScreen = {
                navHostController.navigate(
                    Route.ListRecipe.route
                )
            }
        )
    }
}