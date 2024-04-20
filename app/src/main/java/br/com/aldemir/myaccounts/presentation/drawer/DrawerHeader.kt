package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.GreenMedium
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.LOGO_HEIGHT_MEDIUM
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.Typography
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.theme.drawerHeaderColor
import br.com.aldemir.myaccounts.R
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.theme.Green200
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.expense.presentation.listexpense.ListExpenseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DrawerHeader(
    switchState: Boolean,
    onDarkMode: (Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(MyAccountsTheme.dimensions.sizing120)
            .fillMaxSize()
            .background(color = MaterialTheme.colors.drawerHeaderColor)
            .clip(
                RoundedCornerShape(topEnd = MyAccountsTheme.dimensions.sizing48)
            ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MyAccountsTheme.dimensions.padding8)
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
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = MyAccountsTheme.dimensions.padding16),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly


            ) {
                Text(
                    text = getDarkModeText(switchState),
                    color = White,
                    style = MyAccountsTheme.typography.subTitleMedium,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(MyAccountsTheme.dimensions.sizing8))
                Switch(
                    checked = switchState,
                    onCheckedChange ={
                        onDarkMode(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = GreenMedium,
                        uncheckedThumbColor = Green200,
                        checkedTrackColor = MaterialTheme.colors.secondary,
                        uncheckedTrackColor = MaterialTheme.colors.secondary,
                    )
                )


            }
        }
    }
}

@Composable
private fun getDarkModeText(isDarkMode: Boolean): String {
    return if (isDarkMode) {
        stringResource(id = R.string.drawer_dark_mode_enabled)
    } else {
        stringResource(id = R.string.drawer_dark_mode_disabled)
    }
}

@Preview(showSystemUi = true)
@Composable
fun DrawerHeaderPreview() {
    DrawerHeader(
        switchState = false,
        onDarkMode = {}
    )
}