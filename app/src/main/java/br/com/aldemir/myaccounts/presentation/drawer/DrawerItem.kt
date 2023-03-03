package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.aldemir.myaccounts.presentation.theme.*

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
                .padding(horizontal = MEDIUM_PADDING, vertical = LARGE_PADDING_16)
        ) {
            Icon(
                imageVector = menuItem.imageIcon,
                tint = MaterialTheme.colors.taskItemTextColor,
                contentDescription = null
            )
            Text(
                text = stringResource(id = menuItem.titleResourceId),
                color = MaterialTheme.colors.taskItemTextColor,
                fontSize = FONT_SIZE_18,
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