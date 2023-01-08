package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.aldemir.myaccounts.presentation.theme.addAccountBorderColor
import br.com.aldemir.myaccounts.presentation.theme.addAccountLabelColor

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
                focusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
                unfocusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
                focusedLabelColor = MaterialTheme.colors.addAccountBorderColor,
                unfocusedLabelColor = MaterialTheme.colors.addAccountLabelColor,
                textColor = MaterialTheme.colors.addAccountBorderColor,
                disabledTextColor = MaterialTheme.colors.addAccountBorderColor
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
                    Text(text = selectionOption)
                }
            }
        }
    }

}