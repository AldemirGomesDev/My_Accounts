package br.com.aldemir.navigation.destinations.recipe

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.aldemir.recipe.presentation.changerecipe.ChangeRecipeScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.changeRecipeComposable(
    navHostController: NavHostController
) {
    composable<Routes.ChangeRecipe>(
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
    ) { backStackEntry ->
        val idMonthlyRecipe = backStackEntry.toRoute<Routes.ChangeRecipe>().idMonthlyRecipe
        ChangeRecipeScreen(
            idMonthlyRecipe = idMonthlyRecipe,
            navigateToDetailScreen = {
                navHostController.navigateUp()
            },
        )
    }
}