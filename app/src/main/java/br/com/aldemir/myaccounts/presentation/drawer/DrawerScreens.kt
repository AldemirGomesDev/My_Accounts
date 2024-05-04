package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.myaccounts.R
import br.com.aldemir.navigation.Route

sealed class DrawerScreens(val titleResourceId: Int, val route: String, val imageIcon: ImageVector) {
    data object Home : DrawerScreens(
        titleResourceId = R.string.home_title,
        route = Route.Home.route,
        imageIcon = Icons.Filled.Home
    )
    data object Statistic : DrawerScreens(
        titleResourceId = R.string.expense_add_screen_title,
        route = Route.ExpenseGraphRoute.ExpenseAdd.route, imageIcon =
        Icons.Filled.Add
    )
    data object ListExpense : DrawerScreens(
        titleResourceId = R.string.expense_list_screen_title,
        route = Route.ExpenseGraphRoute.ExpenseList.route,
        imageIcon = Icons.Filled.List
    )
    data object Historic : DrawerScreens(
        titleResourceId = R.string.historic_screen_title,
        route = Route.Historic.route,
        imageIcon = Icons.Filled.List
    )
    data object AddRecipe : DrawerScreens(
        titleResourceId = R.string.recipe_add_screen_title,
        route = Route.AddRecipe.route,
        imageIcon = Icons.Filled.Add
    )
    data object ListRecipe : DrawerScreens(
        titleResourceId = R.string.recipe_list_screen_title,
        route = Route.ListRecipe.route,
        imageIcon = Icons.Filled.List
    )
}

val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Statistic,
    DrawerScreens.ListExpense,
    DrawerScreens.Historic,
    DrawerScreens.AddRecipe,
    DrawerScreens.ListRecipe
)