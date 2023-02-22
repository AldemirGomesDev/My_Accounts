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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.TextTitleLarge
import br.com.aldemir.myaccounts.presentation.home.HomeViewModel
import br.com.aldemir.myaccounts.presentation.theme.*

@Composable
fun DrawerHeader(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    var switchState = viewModel.uiState.value.darkMode

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(120.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colors.drawerHeaderColor)
            .clip(
                RoundedCornerShape(topEnd = 40.dp)
            ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Image(
                    modifier = Modifier
                        .size(LOGO_HEIGHT_MEDIUM),
                    painter = painterResource(id = R.drawable.icon_despesa),
                    contentDescription = stringResource(id = R.string.to_do_logo)
                )
                TextTitleLarge(
                    text = stringResource(id = R.string.expense_welcome),
                    modifier = Modifier.padding(start = LARGE_PADDING)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly


            ) {
                Text(
                    text = stringResource(id = R.string.expense_dark_mode),
                    color = White,
                    style = Typography.h6,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))
                Switch(
                    checked = switchState,
                    onCheckedChange ={
                        switchState = it
                        viewModel.setDarkMode()
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = GreenMedium,
                        uncheckedThumbColor = MaterialTheme.colors.primary,
                        checkedTrackColor = MaterialTheme.colors.secondary,
                        uncheckedTrackColor = MaterialTheme.colors.secondary,
                    )
                )


            }
        }
    }
}