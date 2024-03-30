package br.com.aldemir.myaccounts.presentation.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.myaccounts.R
import br.com.aldemir.navigation.Route

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
        route = Route.ExpenseGraphRoute.route,
        icon = Icons.AutoMirrored.Outlined.List,
    ),
    BottomNavItem(
        resourceNameId = R.string.recipe_list_screen_title,
        route = Route.ListRecipe.route,
        icon = Icons.AutoMirrored.Outlined.List,
    ),
)