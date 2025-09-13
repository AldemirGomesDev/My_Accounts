package br.com.aldemir.common.component

import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.aldemir.common.theme.addAccountBorderColor
import br.com.aldemir.common.theme.taskItemTextColor

@ExperimentalMaterialApi
@Composable
fun MyExposedDropdownMenu(
    label: String,
    listItems: List<String>,
    selected: String = listItems[0],
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit,
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selected,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = modifier,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.addAccountBorderColor,
                unfocusedBorderColor = MaterialTheme.colorScheme.addAccountBorderColor,
                focusedLabelColor = MaterialTheme.colorScheme.addAccountBorderColor,
                unfocusedLabelColor = MaterialTheme.colorScheme.addAccountBorderColor,
                textColor = MaterialTheme.colorScheme.addAccountBorderColor,
                disabledTextColor = MaterialTheme.colorScheme.addAccountBorderColor,
                trailingIconColor = MaterialTheme.colorScheme.addAccountBorderColor
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            listItems.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption, color = MaterialTheme.colorScheme.taskItemTextColor)
                }
            }
        }
    }

}