package br.com.aldemir.myaccounts.presentation.addexpense

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.CheckboxWithText
import br.com.aldemir.myaccounts.presentation.component.InputTextOutlinedTextField
import br.com.aldemir.myaccounts.presentation.component.LoadingButton
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.MaskCurrencyVisualTransformation

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddAccountScreen(
    viewModel: AddExpenseViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val focusManager = LocalFocusManager.current

    val id: Int by viewModel.id
    val name: String by viewModel.name
    val value: String by viewModel.value
    val description: String by viewModel.description

    LaunchedEffect(key1 = id) {
        if (id > 0) navigateToHomeScreen()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                AddAccountContent(
                    viewModel = viewModel,
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
                        focusManager.clearFocus()
                    },
                )
            }
        },
    )
}

@Composable
private fun AddAccountContent(
    viewModel: AddExpenseViewModel,
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

    enabled = (viewModel.isEnabledRegisterButton.value && !isLoading.value)

    InputTextOutlinedTextField(
        value = title,
        onValueChange = {
            onTitleChange(it)
            viewModel.validateName()
        },
        label = stringResource(R.string.form_add_name),
        isError = viewModel.isNameValid.value
    )
    Text(text = viewModel.nameError.value, color = Purple700, fontSize = FONT_SIZE_12)
    Divider(
        modifier = Modifier.height(MEDIUM_PADDING),
        color = MaterialTheme.colors.background
    )
    InputTextOutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            viewModel.validateValue()
        },
        label = stringResource(R.string.form_add_value),
        isError = viewModel.isValueValid.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        visualTransformation = MaskCurrencyVisualTransformation()
    )
    Text(
        text = viewModel.valueError.value,
        color = Purple700,
        fontSize = FONT_SIZE_12
    )
    Divider(
        modifier = Modifier.height(MEDIUM_PADDING),
        color = MaterialTheme.colors.background
    )
    InputTextOutlinedTextField(
        value = description,
        onValueChange = {
            onDescriptionChange(it)
            viewModel.validateDescription()
        },
        label = stringResource(R.string.form_add_description),
        isError = viewModel.isDescriptionValid.value
    )
    Text(
        text = viewModel.descriptionError.value,
        color = Purple700,
        fontSize = FONT_SIZE_12
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MaterialTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.add_expense_text_checkbox),
        isChecked = viewModel.isCheckedPaid.value,
        onCheckedChange = { viewModel.isCheckedPaid.value = it }
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MaterialTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.add_expense_text_checkbox_repeat),
        isChecked = viewModel.isAccountRepeat.value,
        onCheckedChange = { viewModel.isAccountRepeat.value = it }
    )
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

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}