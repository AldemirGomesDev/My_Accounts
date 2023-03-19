package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.expense.addexpense.AddExpenseScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.util.Const.NavigationAnimationDurationMillis
import com.google.accompanist.navigation.animation.composable

@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addAccountComposable(
    navHostController: NavHostController
) {
      composable(
        route = Route.ExpenseAdd.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 },
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
        AddExpenseScreen(
            navigateToHomeScreen = {
                navHostController.navigate(
                    Route.ExpenseList.route
                )
            }
        )
    }
}