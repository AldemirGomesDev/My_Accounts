package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.myaccounts.presentation.theme.addAccountBorderColor
import br.com.aldemir.myaccounts.presentation.theme.addAccountLabelColor

@Composable
fun InputTextOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        capitalization = KeyboardCapitalization.Sentences
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            focusedLabelColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedLabelColor = MaterialTheme.colors.addAccountLabelColor,
            textColor = MaterialTheme.colors.addAccountBorderColor,
            disabledTextColor = MaterialTheme.colors.addAccountBorderColor
        ),
        isError = isError,
        trailingIcon = {
            if (isError) Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = ""
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewComponent() {
    InputTextOutlinedTextField(
        value = "",
        label = "Nome",
        isError = false,
        onValueChange = {}
    )
}