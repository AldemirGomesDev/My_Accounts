package br.com.aldemir.myaccounts.presentation.drawer

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme

@Composable
fun DrawerItem(
    menuItem: DrawerScreens,
    modifier: Modifier = Modifier,
    onItemClick: (DrawerScreens) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onItemClick(menuItem)
        }
            .background(MyAccountsTheme.colors.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(
                    horizontal = MyAccountsTheme.dimensions.padding8,
                    vertical = MyAccountsTheme.dimensions.padding16
                )
                .background(MyAccountsTheme.colors.background)
        ) {
            Icon(
                imageVector = menuItem.imageIcon,
                tint = MyAccountsTheme.colors.primary,
                contentDescription = null
            )
            Text(
                text = stringResource(id = menuItem.titleResourceId),
                color = MyAccountsTheme.colors.primary,
                fontSize = FontSize.scale18,
                modifier = Modifier
                    .padding(
                        horizontal = MyAccountsTheme.dimensions.padding10
                    )
            )
        }
        Divider(color = MyAccountsTheme.colors.onBackground)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DrawerPreview() {
    DrawerItem(
        menuItem = DrawerScreens.Home,
        onItemClick = {}
    )
}