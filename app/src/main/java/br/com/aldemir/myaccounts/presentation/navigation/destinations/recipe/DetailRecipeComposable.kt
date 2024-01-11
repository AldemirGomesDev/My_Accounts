package br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.recipe.presentation.detail.DetailRecipeScreen
import br.com.aldemir.common.util.Const
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.detailRecipeComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.DetailRecipe.route,
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
        arguments = listOf(
            navArgument(Const.RECIPE_ID) {
                type = NavType.IntType
            },
        )
    ) { backStackEntry ->
        val recipeId = backStackEntry.arguments?.getInt(Const.RECIPE_ID)
        DetailRecipeScreen(
            recipeId = recipeId ?: 0,
            navigateToChangeScreen = { idRecipe ->
                navHostController.navigate(
                    Route.ChangeRecipe.createRoute(idRecipe)
                )
            },
            navigateToBackScreen = { navHostController.navigateUp() }
        )
    }
}