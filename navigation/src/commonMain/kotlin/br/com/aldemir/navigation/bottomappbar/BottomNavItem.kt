package br.com.aldemir.navigation.bottomappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.navigation.Routes
import myaccounts.navigation.generated.resources.Res
import myaccounts.navigation.generated.resources.expense_list_screen_title
import myaccounts.navigation.generated.resources.home_title
import myaccounts.navigation.generated.resources.recipe_list_screen_title
import org.jetbrains.compose.resources.StringResource

data class BottomNavItem(
    val resourceNameId: StringResource,
    val route: Any,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        resourceNameId = Res.string.home_title,
        route = Routes.Home,
        icon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        resourceNameId = Res.string.expense_list_screen_title,
        route = Routes.ExpenseGraphRoute.ExpenseList,
        icon = Icons.AutoMirrored.Outlined.List,
    ),
    BottomNavItem(
        resourceNameId = Res.string.recipe_list_screen_title,
        route = Routes.ListRecipe,
        icon = Icons.AutoMirrored.Outlined.List,
    ),
)