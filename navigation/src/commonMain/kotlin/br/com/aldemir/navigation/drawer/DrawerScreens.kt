package br.com.aldemir.navigation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.navigation.R
import br.com.aldemir.navigation.Routes

sealed class DrawerScreens(val titleResourceId: Int, val route: Any, val imageIcon: ImageVector) {
    data object Home : DrawerScreens(
        titleResourceId = R.string.home_title,
        route = Routes.Home,
        imageIcon = Icons.Filled.Home
    )
    data object Statistic : DrawerScreens(
        titleResourceId = R.string.expense_add_screen_title,
        route = Routes.ExpenseGraphRoute.ExpenseAdd, imageIcon =
        Icons.Filled.Add
    )
    data object ListExpense : DrawerScreens(
        titleResourceId = R.string.expense_list_screen_title,
        route = Routes.ExpenseGraphRoute.ExpenseList,
        imageIcon = Icons.Filled.List
    )
    data object Historic : DrawerScreens(
        titleResourceId = R.string.historic_screen_title,
        route = Routes.Historic,
        imageIcon = Icons.Filled.List
    )
    data object AddRecipe : DrawerScreens(
        titleResourceId = R.string.recipe_add_screen_title,
        route = Routes.AddRecipe,
        imageIcon = Icons.Filled.Add
    )
    data object ListRecipe : DrawerScreens(
        titleResourceId = R.string.recipe_list_screen_title,
        route = Routes.ListRecipe,
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