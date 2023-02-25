package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
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
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Text,
        capitalization = KeyboardCapitalization.Sentences,
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = { focusManager.clearFocus() }
        ),
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