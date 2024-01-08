package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aldemir.myaccounts.presentation.shared.model.DropdownItemState
import br.com.aldemir.myaccounts.presentation.shared.model.DropdownItemType
import br.com.aldemir.myaccounts.presentation.theme.*

@Composable
fun MyDropdownMenuItem(
    onItemClicked: (type: DropdownItemType) -> Unit,
    listItems: Array<DropdownItemState>,
    disabledItem: Boolean = true,
) {
    val contextForToast = LocalContext.current.applicationContext

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Open Options",
                tint = MaterialTheme.colors.taskItemTextColor,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            listItems.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        onItemClicked(itemValue.type)
                        expanded = false
                    },
                    enabled = disabledItem
                ) {
                    Icon(
                        modifier = Modifier.padding(end = MEDIUM_PADDING),
                        imageVector = itemValue.icon,
                        tint = MaterialTheme.colors.taskItemTextColor,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = itemValue.titleRes),
                        color = MaterialTheme.colors.taskItemTextColor
                    )
                }
                if (itemIndex < listItems.lastIndex)
                    Divider(color = DarkGray, thickness = 1.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropdownMenuItemPreview() {
    MyDropdownMenuItem(
        onItemClicked = {},
        listItems = arrayOf()
    )
}