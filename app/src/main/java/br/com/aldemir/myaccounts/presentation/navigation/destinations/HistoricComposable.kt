package br.com.aldemir.myaccounts.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import br.com.aldemir.myaccounts.presentation.historic.HistoricScreen
import br.com.aldemir.myaccounts.presentation.navigation.Route

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
       HistoricScreen(
           navigateToHistoricScreen = { expenseId, expenseName ->
               navHostController.navigate(
                   Route.ExpenseDetail.createRoute(expenseId, expenseName)
               )
           }
       )
   }
}