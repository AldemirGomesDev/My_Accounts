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
import androidx.navigation.navArgument
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.presentation.recipe.detail.DetailRecipeScreen
import br.com.aldemir.myaccounts.util.Const
import br.com.aldemir.myaccounts.util.emptyString
import com.google.accompanist.navigation.animation.composable

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
        arguments = listOf(
            navArgument(Const.RECIPE_ID) {
                type = NavType.IntType
            },
            navArgument(Const.RECIPE_NAME) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val recipeId = backStackEntry.arguments?.getInt(Const.RECIPE_ID)
        val recipeName = backStackEntry.arguments?.getString(Const.RECIPE_NAME)
        DetailRecipeScreen(
            recipeId = recipeId ?: 0,
            recipeName = recipeName ?: emptyString(),
            navigateToChangeScreen = { idRecipe ->
                navHostController.navigate(
                    Route.ExpenseChange.createRoute(
                        idRecipe,
                        recipeName ?: emptyString()
                    )
                )
            },
            navigateToBackScreen = { navHostController.navigateUp() }
        )
    }
}