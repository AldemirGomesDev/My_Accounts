package br.com.aldemir.navigation.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.navigation.R
import br.com.aldemir.navigation.Routes

data class BottomNavItem(
    val resourceNameId: Int,
    val route: Any,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        resourceNameId = R.string.home_title,
        route = Routes.Home,
        icon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        resourceNameId = R.string.expense_list_screen_title,
        route = Routes.ExpenseGraphRoute.ExpenseList,
        icon = Icons.AutoMirrored.Outlined.List,
    ),
    BottomNavItem(
        resourceNameId = R.string.recipe_list_screen_title,
        route = Routes.ListRecipe,
        icon = Icons.AutoMirrored.Outlined.List,
    ),
)