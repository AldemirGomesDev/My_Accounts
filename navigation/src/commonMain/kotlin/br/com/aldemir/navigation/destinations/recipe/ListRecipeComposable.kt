package br.com.aldemir.navigation.destinations.recipe

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.recipe.presentation.list.ListRecipeScreen
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.navigation.Routes

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.listRecipeComposable(
    navHostController: NavHostController
) {
    composable<Routes.ListRecipe>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(NavigationAnimationDurationMillis)
            )
        },
    ) {
        ListRecipeScreen(
            navigateToDetailScreen = { recipeId ->
                navHostController.navigate(
                    Routes.DetailRecipe(recipeId)
                )
            },
            navigateToHomeScreen = {
                navHostController.navigate(
                    Routes.Home
                )
            },
            navigateToAddRecipeScreen = {
                navHostController.navigate(
                    Routes.AddRecipe
                )
            },
        )
    }
}