package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis
import br.com.aldemir.expense.presentation.addexpense.AddExpenseScreen
import br.com.aldemir.navigation.Routes

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addAccountComposable(
    navHostController: NavHostController
) {
      composable<Routes.ExpenseGraphRoute.ExpenseAdd>(
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
        AddExpenseScreen(
            navigateToBack = {
                navHostController.navigateUp()
            }
        )
    }
}