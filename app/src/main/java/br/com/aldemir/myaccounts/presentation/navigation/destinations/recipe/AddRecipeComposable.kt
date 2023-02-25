package br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.presentation.recipe.addrecipe.AddRecipeScreen
import com.google.accompanist.navigation.animation.composable

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
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500))
        },
    ) {
        AddRecipeScreen(
            navigateToHomeScreen = {
                navHostController.navigate(
                    Route.Home.route
                )
            }
        )
    }
}