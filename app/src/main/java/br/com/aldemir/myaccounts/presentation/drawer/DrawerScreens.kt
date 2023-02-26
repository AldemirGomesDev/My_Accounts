package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.navigation.Route

sealed class DrawerScreens(val titleResourceId: Int, val route: String, val imageIcon: ImageVector) {
    object Home : DrawerScreens(
        titleResourceId = R.string.home,
        route = Route.Home.route,
        imageIcon = Icons.Filled.Home
    )
    object Statistic : DrawerScreens(
        titleResourceId = R.string.add_account_screen_title,
        route = Route.ExpenseAdd.route, imageIcon =
        Icons.Filled.Add
    )
    object ListExpense : DrawerScreens(
        titleResourceId = R.string.expense_list,
        route = Route.ExpenseList.route,
        imageIcon = Icons.Filled.List
    )
    object Historic : DrawerScreens(
        titleResourceId = R.string.historic,
        route = Route.Historic.route,
        imageIcon = Icons.Filled.List
    )
    object AddRecipe : DrawerScreens(
        titleResourceId = R.string.add_revenue_screen_title,
        route = Route.AddRecipe.route,
        imageIcon = Icons.Filled.Add
    )
    object ListRecipe : DrawerScreens(
        titleResourceId = R.string.list_recipe_screen_title,
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