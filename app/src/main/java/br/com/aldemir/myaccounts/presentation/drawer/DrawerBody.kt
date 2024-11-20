package br.com.aldemir.myaccounts.presentation.drawer

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerBody(
    menuItems: List<DrawerScreens>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    onItemClick: (DrawerScreens) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = MyAccountsTheme.colors.background)
    ) {
        items(menuItems) { item ->
            DrawerItem(
                item,
                modifier = modifier
            ) {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                onItemClick(item)
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DrawerBodyPreview() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    MyAccountsTheme {
        DrawerBody(
            menuItems = screens,
            scaffoldState = scaffoldState,
            scope = scope,
            onItemClick = {}
        )
    }
}