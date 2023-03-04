package br.com.aldemir.myaccounts.presentation.recipe.addrecipe


import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.CheckboxWithText
import br.com.aldemir.myaccounts.presentation.component.InputTextOutlinedTextField
import br.com.aldemir.myaccounts.presentation.component.LoadingButton
import br.com.aldemir.myaccounts.presentation.component.MyExposedDropdownMenu
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.MaskCurrencyVisualTransformation
import java.util.*

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddRecipeScreen(
    viewModel: AddRecipeViewModel = hiltViewModel(),
    navigateToListRecipeScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val focusManager = LocalFocusManager.current

    val id: Int by viewModel.id
    val name: String by viewModel.name
    val value: String by viewModel.value
    val description: String by viewModel.description

    BackHandler {
        navigateToListRecipeScreen()
    }

    LaunchedEffect(key1 = id) {
        if (id > 0) navigateToListRecipeScreen()
    }
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(10) }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(state)
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
                        viewModel.saveAccount()
                        focusManager.clearFocus()
                    },
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddAccountContent(
    viewModel: AddRecipeViewModel,
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
    val repeatOptions = stringArrayResource(id = R.array.numbers)
    val dueDateOptions = stringArrayResource(id = R.array.days)

    var dueDateOptionSelected by remember { mutableStateOf(dueDateOptions[0]) }

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
    Text(
        text = viewModel.nameError.value,
        color = MaterialTheme.colors.error,
        fontSize = FONT_SIZE_12
    )
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next,
        ),
        visualTransformation = MaskCurrencyVisualTransformation()
    )
    Text(
        text = viewModel.valueError.value,
        color = MaterialTheme.colors.error,
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
        isError = viewModel.isDescriptionValid.value,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Text(
        text = viewModel.descriptionError.value,
        color = MaterialTheme.colors.error,
        fontSize = FONT_SIZE_12
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MaterialTheme.colors.background
    )
    MyExposedDropdownMenu(
        label = stringResource(id = R.string.form_due_date_day),
        listItems = dueDateOptions.toList(),
        selected = dueDateOptionSelected,
        onItemSelected = { item ->
            dueDateOptionSelected = item
            viewModel.dueDateSelected.value = item.toInt()
        },
        modifier = Modifier.fillMaxWidth()
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MaterialTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.form_text_checkbox),
        isChecked = viewModel.isCheckedPaid.value,
        onCheckedChange = { viewModel.isCheckedPaid.value = it }
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MaterialTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.form_text_checkbox_repeat),
        isChecked = viewModel.isAccountRepeat.value,
        onCheckedChange = {
            viewModel.isAccountRepeat.value = it
            viewModel.clearRepeatAmount(it)
        }
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MaterialTheme.colors.background
    )
    if (viewModel.isAccountRepeat.value) {
        MyExposedDropdownMenu(
            label = stringResource(id = R.string.form_how_many_times_repeat),
            listItems = repeatOptions.toList(),
            selected = viewModel.amountThatRepeatsSelected.value.toString(),
            onItemSelected = { item ->
                viewModel.amountThatRepeatsSelected.value = item.toInt()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Divider(
            modifier = Modifier.height(LARGEST_PADDING),
            color = MaterialTheme.colors.background
        )
    }
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
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Purple200,
        ),
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.button_add_text),
            fontSize = FONT_SIZE_16,
        )
    }
}