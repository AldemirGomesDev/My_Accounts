package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import br.com.aldemir.common.navigation.Route

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.expenseGraph(
    navHostController: NavHostController,
) {
    navigation(
        startDestination = Route.ExpenseGraphRoute.ExpenseList.route,
        route = Route.ExpenseGraphRoute.route
    ) {
        addAccountComposable(navHostController)
        changeExpenseComposable(navHostController)
        detailExpenseComposable(navHostController)
        listExpenseComposable(navHostController)
    }
}
