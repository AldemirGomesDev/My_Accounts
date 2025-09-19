package br.com.aldemir.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.util.emptyString
import org.jetbrains.compose.resources.stringResource

@Composable
fun DarkModeDropDownMenu(
    modifier: Modifier = Modifier,
    darkModeStateSelected: AppDarkMode,
    onItemClicked: (state: AppDarkMode) -> Unit,
    listItems: List<AppDarkMode>,
    tintColor: Color = MyAccountsTheme.colors.second
) {

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier
            .clip(CircleShape)
            .border(
                width = MyAccountsTheme.dimensions.sizing2,
                color = tintColor,
                shape = CircleShape
            ).clickable { expanded = true }
            .background(MyAccountsTheme.colors.backgroundGreen)
            .padding(
                vertical = MyAccountsTheme.dimensions.padding6,
                horizontal = MyAccountsTheme.dimensions.padding12
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(darkModeStateSelected.titleRes),
            color = tintColor,
            style = MyAccountsTheme.typography.subTitleMedium,
        )
        Spacer(Modifier.width(MyAccountsTheme.dimensions.sizing4))
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = darkModeStateSelected.imageVector,
                contentDescription = emptyString(),
                tint = tintColor
            )
            DropdownMenu(
                modifier = Modifier.background(MyAccountsTheme.colors.background),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                listItems.forEachIndexed { itemIndex, itemValue ->
                    DropdownMenuItem(
                        onClick = {
                            onItemClicked(itemValue)
                            expanded = false
                        },
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = MyAccountsTheme.dimensions.padding8),
                            imageVector = itemValue.imageVector,
                            tint = MyAccountsTheme.colors.primary,
                            contentDescription = emptyString()
                        )
                        Text(
                            text = stringResource(itemValue.titleRes),
                            color = MyAccountsTheme.colors.primary
                        )
                    }
                    if (itemIndex < listItems.lastIndex)
                        Divider(color = MyAccountsTheme.colors.onBackground, thickness = 1.dp)
                }
            }
        }

    }
}

@Composable
private fun DarkModeDropDownMenuPreview() {
    MyAccountsTheme {
        DarkModeDropDownMenu(
            darkModeStateSelected = AppDarkMode.Default,
            onItemClicked = {},
            listItems = listOf(),
            tintColor = White
        )
    }
}