package br.com.aldemir.navigation.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.navigation.Routes
import myaccounts.navigation.generated.resources.Res
import myaccounts.navigation.generated.resources.drawer_logout
import myaccounts.navigation.generated.resources.expense_add_screen_title
import myaccounts.navigation.generated.resources.expense_list_screen_title
import myaccounts.navigation.generated.resources.historic_screen_title
import myaccounts.navigation.generated.resources.home_title
import myaccounts.navigation.generated.resources.recipe_add_screen_title
import myaccounts.navigation.generated.resources.recipe_list_screen_title
import org.jetbrains.compose.resources.StringResource

sealed class DrawerScreens(val titleResourceId: StringResource, val route: Any, val imageIcon: ImageVector) {
    data object Home : DrawerScreens(
        titleResourceId = Res.string.home_title,
        route = Routes.Home,
        imageIcon = Icons.Filled.Home
    )
    data object Statistic : DrawerScreens(
        titleResourceId = Res.string.expense_add_screen_title,
        route = Routes.ExpenseGraphRoute.ExpenseAdd, imageIcon =
        Icons.Filled.Add
    )
    data object ListExpense : DrawerScreens(
        titleResourceId = Res.string.expense_list_screen_title,
        route = Routes.ExpenseGraphRoute.ExpenseList,
        imageIcon = Icons.Filled.List
    )
    data object Historic : DrawerScreens(
        titleResourceId = Res.string.historic_screen_title,
        route = Routes.Historic,
        imageIcon = Icons.Filled.List
    )
    data object AddRecipe : DrawerScreens(
        titleResourceId = Res.string.recipe_add_screen_title,
        route = Routes.AddRecipe,
        imageIcon = Icons.Filled.Add
    )
    data object ListRecipe : DrawerScreens(
        titleResourceId = Res.string.recipe_list_screen_title,
        route = Routes.ListRecipe,
        imageIcon = Icons.Filled.List
    )
    data object Logout : DrawerScreens(
        titleResourceId = Res.string.drawer_logout,
        route = Routes.Logout,
        imageIcon = Icons.Filled.Close
    )
}

val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Statistic,
    DrawerScreens.ListExpense,
    DrawerScreens.Historic,
    DrawerScreens.AddRecipe,
    DrawerScreens.ListRecipe,
    DrawerScreens.Logout,
)