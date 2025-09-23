package br.com.aldemir.navigation.bottomappbar

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.White
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        elevation = 5.dp,
        backgroundColor = MyAccountsTheme.colors.backgroundGreen,
        contentColor = White
    ) {
        bottomNavItems.forEach { item ->
            val selected = backStackEntry.value?.destination?.route?.endsWith(item.route.toString()) ?: false
            BottomNavigationItem(
                selected = selected,
                label = {
                    Text(
                        text = stringResource(item.resourceNameId),
                        fontWeight = FontWeight.Normal,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.resourceNameId} Icon",
                    )
                },
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

@Composable
private fun BottomBarPreview() {
    MyAccountsTheme {
        BottomBar(navController = rememberNavController())
    }
}