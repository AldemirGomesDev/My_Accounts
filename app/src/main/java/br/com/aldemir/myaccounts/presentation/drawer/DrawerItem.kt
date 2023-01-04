package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.aldemir.myaccounts.presentation.theme.White

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
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(8.dp)
        ) {
            Icon(
                imageVector = menuItem.imageIcon,
                tint = Color.Black,
                contentDescription = null
            )
            Text(
                text = stringResource(id = menuItem.titleResourceId),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        Divider()
    }
}

@Preview
@Composable
fun DrawerPreview() {
    DrawerItem(menuItem = DrawerScreens.Home, onItemClick = {})
}