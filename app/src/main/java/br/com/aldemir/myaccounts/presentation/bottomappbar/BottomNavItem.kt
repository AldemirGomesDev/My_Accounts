package br.com.aldemir.myaccounts.presentation.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
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
        resourceNameId = R.string.home_title,
        route = Route.Home.route,
        icon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        resourceNameId = R.string.expense_list_screen_title,
        route = Route.ExpenseList.route,
        icon = Icons.Outlined.List,
    ),
    BottomNavItem(
        resourceNameId = R.string.recipe_list_screen_title,
        route = Route.ListRecipe.route,
        icon = Icons.Outlined.List,
    ),
)