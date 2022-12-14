package br.com.aldemir.myaccounts.ui.expense

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.ui.component.LoadingButton
import br.com.aldemir.myaccounts.ui.theme.*
import br.com.aldemir.myaccounts.util.MaskCurrencyVisualTransformation

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddAccountScreen(
    viewModel: AddExpenseViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val id: Int by viewModel.id
    val name: String by viewModel.name
    val value: String by viewModel.value
    val description: String by viewModel.description

    LaunchedEffect(key1 = id) {
        if (id > 0) navigateToHomeScreen()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_account_screen_title), color = White)
                },
                backgroundColor = MaterialTheme.colors.topAppBarBackGroundColor
            )
        },
        content = { padding ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)) {
                AddAccountContent(
                    title = name,
                    onTitleChange = {
                        viewModel.name.value = it
                    },
                    value = value,
                    onValueChange = {
                      viewModel.value.value = it
                    },
                    description = description,
                    onDescriptionChange = {
                        viewModel.description.value = it
                    },
                    onClickSave = {
                        viewModel.addAccount()
                    }
                )
            }
        },
    )
}

@Composable
private fun AddAccountContent(
    title: String,
    onTitleChange: (String) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onClickSave: () -> Unit
) {

    val isLoading = remember { mutableStateOf(false) }

    var enabled by remember {
        mutableStateOf(false)
    }

    enabled = (value.isNotEmpty() && description.isNotEmpty() && title.isNotEmpty() && !isLoading.value)

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = title,
        onValueChange = { onTitleChange(it) },
        label = { Text(text = stringResource(R.string.form_add_name)) },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            focusedLabelColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedLabelColor = MaterialTheme.colors.addAccountLabelColor,
            textColor = MaterialTheme.colors.addAccountBorderColor,
            disabledTextColor = MaterialTheme.colors.addAccountBorderColor
        ),
        isError = title.length < 4,
        trailingIcon = { if (title.length < 4) Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
    )
    if (title.length < 4) Text(text = stringResource(id = R.string.invalid_name), color = Purple700, fontSize = FONT_SIZE_12)
    Divider(
        modifier = Modifier.height(MEDIUM_PADDING),
        color = MaterialTheme.colors.background
    )
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = stringResource(R.string.form_add_value)) },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        visualTransformation = MaskCurrencyVisualTransformation(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            focusedLabelColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedLabelColor = MaterialTheme.colors.addAccountLabelColor,
            textColor = MaterialTheme.colors.addAccountBorderColor,
            disabledTextColor = MaterialTheme.colors.addAccountBorderColor
        ),
        isError = value.isEmpty(),
        trailingIcon = { if (value.isEmpty()) Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
    )
    if (value.isEmpty()) Text(text = stringResource(id = R.string.invalid_value), color = Purple700, fontSize = FONT_SIZE_12)
    Divider(
        modifier = Modifier.height(MEDIUM_PADDING),
        color = MaterialTheme.colors.background
    )
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = description,
        onValueChange = { onDescriptionChange(it) },
        label = { Text(text = stringResource(R.string.form_add_description)) },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
            focusedLabelColor = MaterialTheme.colors.addAccountBorderColor,
            unfocusedLabelColor = MaterialTheme.colors.addAccountLabelColor,
            textColor = MaterialTheme.colors.addAccountBorderColor,
            disabledTextColor = MaterialTheme.colors.addAccountBorderColor
        ),
        isError = description.isEmpty(),
        trailingIcon = { if (description.isEmpty()) Icon(imageVector = Icons.Filled.Info, contentDescription = "") },
    )
    if (description.isEmpty()) Text(text = stringResource(id = R.string.invalid_description), color = Purple700, fontSize = FONT_SIZE_12)
    Divider(
        modifier = Modifier.height(LARGEST_PADDING),
        color = MaterialTheme.colors.background
    )
    LoadingButton(
        onClick = {
            isLoading.value = true
            onClickSave()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        loading = isLoading.value,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.add_account),
            fontSize = FONT_SIZE_16,
        )
    }
}