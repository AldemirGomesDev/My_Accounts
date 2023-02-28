package br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.presentation.recipe.list.ListRecipeScreen
import br.com.aldemir.myaccounts.util.Const.NavigationAnimationDurationMillis
import com.google.accompanist.navigation.animation.composable

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
            navigateToDetailScreen = { recipeId, recipeName ->
                navHostController.navigate(
                    Route.DetailRecipe.createRoute(recipeId, recipeName)
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
            }
        )
    }
}