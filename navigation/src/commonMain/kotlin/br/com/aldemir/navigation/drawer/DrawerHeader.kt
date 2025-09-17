package br.com.aldemir.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import br.com.aldemir.common.component.DarkModeDropDownMenu
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.navigation.R

@Composable
internal fun DrawerHeader(
    listItems: List<AppDarkMode>,
    onItemClicked: (state: AppDarkMode) -> Unit,
    darkModeStateSelected: AppDarkMode,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MyAccountsTheme.colors.backgroundGreen)
            .clip(
                RoundedCornerShape(topEnd = MyAccountsTheme.dimensions.sizing48)
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MyAccountsTheme.dimensions.padding16),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MyAccountsTheme.dimensions.padding8)
            ) {
                Image(
                    modifier = Modifier
                        .size(MyAccountsTheme.dimensions.sizing52),
                    painter = painterResource(
                        id = getLogo(appDarkMode = darkModeStateSelected)
                    ),
                    contentDescription = null
                )
                TextTitleLarge(
                    text = stringResource(id = R.string.drawer_welcome),
                    modifier = Modifier.padding(start = MyAccountsTheme.dimensions.padding12)
                )
            }
            Spacer(Modifier.height(MyAccountsTheme.dimensions.sizing12))
            DarkModeDropDownMenu(
                onItemClicked = {
                    onItemClicked(it)
                },
                listItems = listItems,
                darkModeStateSelected = darkModeStateSelected,
                tintColor = MyAccountsTheme.colors.second
            )
        }
    }
}

@Composable
private fun getLogo(appDarkMode: AppDarkMode): Int {
    val isDarkMode = when(appDarkMode) {
        AppDarkMode.Default -> isSystemInDarkTheme()
        AppDarkMode.Dark -> true
        AppDarkMode.Light -> false
    }
    return if (isDarkMode) {
        R.drawable.icon_despesa_light
    } else {
        R.drawable.icon_despesa
    }
}

@Composable
fun DrawerHeaderPreview() {
    MyAccountsTheme {
        DrawerHeader(
            darkModeStateSelected = AppDarkMode.Default,
            onItemClicked = {},
            listItems = listOf()
        )
    }
}