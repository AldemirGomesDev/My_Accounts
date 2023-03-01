package br.com.aldemir.myaccounts.presentation.bottomappbar

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.aldemir.myaccounts.presentation.theme.White

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = White
    ) {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                label = {
                    Text(
                        text = stringResource(id = item.resourceNameId),
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