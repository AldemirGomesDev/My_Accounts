package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.component.DarkModeDropDownMenu
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.theme.drawerHeaderColor
import br.com.aldemir.myaccounts.R
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.theme.DarkModeDropDownState
import br.com.aldemir.common.theme.MyAccountsTheme

@Composable
internal fun DrawerHeader(
    listItems: List<DarkModeDropDownState>,
    onItemClicked: (state: DarkModeDropDownState) -> Unit,
    darkModeStateSelected: DarkModeDropDownState,
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.drawerHeaderColor)
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
                    painter = painterResource(id = R.drawable.icon_despesa),
                    contentDescription = stringResource(id = R.string.account_logo)
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
                tintColor = White
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DrawerHeaderPreview() {
    DrawerHeader(
        darkModeStateSelected = DarkModeDropDownState(),
        onItemClicked = {},
        listItems = listOf()
    )
}