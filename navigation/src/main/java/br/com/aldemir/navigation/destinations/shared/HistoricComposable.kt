package br.com.aldemir.navigation.destinations.shared

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.aldemir.expense.presentation.historic.HistoricScreen
import br.com.aldemir.navigation.Route
import br.com.aldemir.common.util.Const.NavigationAnimationDurationMillis

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.historicComposable(
    navHostController: NavHostController
) {
   composable(
       route = Route.Historic.route,
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
       HistoricScreen(
           navigateToHistoricScreen = { expenseId, expenseName ->
               navHostController.navigate(
                   Route.ExpenseDetail.createRoute(expenseId, expenseName)
               )
           }
       )
   }
}