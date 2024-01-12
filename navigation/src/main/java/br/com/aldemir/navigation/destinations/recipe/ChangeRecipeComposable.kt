package br.com.aldemir.navigation.destinations.recipe

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
import br.com.aldemir.navigation.Route
import br.com.aldemir.recipe.presentation.changerecipe.ChangeRecipeScreen
import br.com.aldemir.common.util.Const
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.changeRecipeComposable(
    navHostController: NavHostController
) {
    composable(
        route = Route.ChangeRecipe.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(NavigationAnimationDurationMillis))
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
        arguments = listOf(
            navArgument(Const.RECIPE_ID) {
                type = NavType.IntType
            },
        )
    ) { backStackEntry ->
        val idMonthlyRecipe = backStackEntry.arguments?.getInt(Const.RECIPE_ID)
        ChangeRecipeScreen(
            idMonthlyRecipe = idMonthlyRecipe ?: 0,
            navigateToDetailScreen = {
                navHostController.navigateUp()
            },
        )
    }
}