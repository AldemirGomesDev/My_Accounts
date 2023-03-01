package br.com.aldemir.myaccounts.presentation.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.navigation.Route

data class BottomNavItem(
    val resourceNameId: Int,
    val route: String,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        resourceNameId = R.string.home,
        route = Route.Home.route,
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        resourceNameId = R.string.list_expense_screen_title,
        route = Route.ExpenseList.route,
        icon = Icons.Rounded.AddCircle,
    ),
    BottomNavItem(
        resourceNameId = R.string.list_recipe_screen_title,
        route = Route.ListRecipe.route,
        icon = Icons.Rounded.List,
    ),
)