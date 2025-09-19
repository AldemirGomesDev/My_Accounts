package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import br.com.aldemir.navigation.Routes

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
fun NavGraphBuilder.expenseGraph(
    navHostController: NavHostController,
) {
    navigation(
        startDestination = Routes.ExpenseGraphRoute.ExpenseList.toString(),
        route = Routes.ExpenseGraphRoute.toString()
    ) {
        addAccountComposable(navHostController)
        changeExpenseComposable(navHostController)
        detailExpenseComposable(navHostController)
        listExpenseComposable(navHostController)
    }
}
