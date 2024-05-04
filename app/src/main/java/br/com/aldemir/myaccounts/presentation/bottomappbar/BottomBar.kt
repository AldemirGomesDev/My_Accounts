package br.com.aldemir.myaccounts.presentation.bottomappbar

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.White

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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun BottomBarPreview() {
    MyAccountsTheme {
        BottomBar(navController = rememberNavController())
    }
}