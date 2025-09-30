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
import br.com.aldemir.common.component.DarkModeDropDownMenu
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.model.UserLogged
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.drawer_welcome
import myaccounts.common.generated.resources.icon_despesa
import myaccounts.common.generated.resources.icon_despesa_light
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrawerHeader(
    userLogged: UserLogged,
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
                    painter = painterResource(getLogo(appDarkMode = darkModeStateSelected)),
                    contentDescription = null
                )
                Column {
                    TextTitleLarge(
                        text = stringResource(Res.string.drawer_welcome),
                        modifier = Modifier.padding(start = MyAccountsTheme.dimensions.padding12)
                    )
                    TextSubTitleItem(
                        text = userLogged.name,
                        color = MyAccountsTheme.colors.second,
                        modifier = Modifier.padding(
                            start = MyAccountsTheme.dimensions.padding12,
                            top = MyAccountsTheme.dimensions.padding4
                        )
                    )
                }
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
private fun getLogo(appDarkMode: AppDarkMode): DrawableResource {
    val isDarkMode = when(appDarkMode) {
        AppDarkMode.Default -> isSystemInDarkTheme()
        AppDarkMode.Dark -> true
        AppDarkMode.Light -> false
    }
    return if (isDarkMode) {
        Res.drawable.icon_despesa_light
    } else {
        Res.drawable.icon_despesa
    }
}

@Composable
fun DrawerHeaderPreview() {
    MyAccountsTheme {
        DrawerHeader(
            userLogged = UserLogged(name = "Aldemir"),
            darkModeStateSelected = AppDarkMode.Default,
            onItemClicked = {},
            listItems = listOf()
        )
    }
}