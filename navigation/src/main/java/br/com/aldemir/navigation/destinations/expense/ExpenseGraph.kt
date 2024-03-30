package br.com.aldemir.navigation.destinations.expense

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import br.com.aldemir.common.R
import br.com.aldemir.navigation.Route
import br.com.aldemir.navigation.state.TopBarState

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
