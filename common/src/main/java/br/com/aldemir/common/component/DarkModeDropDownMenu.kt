package br.com.aldemir.common.component

import android.content.res.Configuration
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import br.com.aldemir.common.theme.DarkModeDropDownState
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.util.emptyString

@Composable
fun DarkModeDropDownMenu(
    modifier: Modifier = Modifier,
    darkModeStateSelected: DarkModeDropDownState,
    onItemClicked: (state: DarkModeDropDownState) -> Unit,
    listItems: List<DarkModeDropDownState>,
    tintColor: Color = MyAccountsTheme.colors.primary
) {

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier
            .clip(CircleShape)
            .border(
                width = MyAccountsTheme.dimensions.sizing2,
                color = White,
                shape = CircleShape
            ).clickable { expanded = true }
            .padding(
                vertical = MyAccountsTheme.dimensions.padding6,
                horizontal = MyAccountsTheme.dimensions.padding12
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = darkModeStateSelected.titleRes),
            color = White,
            style = MyAccountsTheme.typography.subTitleMedium,
        )
        Spacer(Modifier.width(MyAccountsTheme.dimensions.sizing4))
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = darkModeStateSelected.iconRes),
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
                            imageVector = ImageVector.vectorResource(itemValue.iconRes),
                            tint = MyAccountsTheme.colors.primary,
                            contentDescription = emptyString()
                        )
                        Text(
                            text = stringResource(id = itemValue.titleRes),
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

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = 0xFF009688
)
@Composable
private fun DarkModeDropDownMenuPreview() {
    DarkModeDropDownMenu(
        darkModeStateSelected = DarkModeDropDownState(),
        onItemClicked = {},
        listItems = listOf()
    )
}