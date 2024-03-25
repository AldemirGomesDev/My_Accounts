package br.com.aldemir.expense.presentation.addexpense

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import br.com.aldemir.common.theme.Purple200
import br.com.aldemir.common.R
import br.com.aldemir.common.component.CheckboxWithText
import br.com.aldemir.common.component.CustomSnackBar
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.component.MyExposedDropdownMenu
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.MaskCurrencyVisualTransformation
import br.com.aldemir.common.util.getCurrencySymbol
import org.koin.androidx.compose.koinViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel = koinViewModel(),
    navigateToHomeScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val focusManager = LocalFocusManager.current

    val name: String by viewModel.name
    val value: String by viewModel.value
    val description: String by viewModel.description

    BackHandler {
        navigateToHomeScreen()
    }

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(10) }

    val messageError = stringResource(id = R.string.expense_save_error)
    val messageSuccess = stringResource(id = R.string.expense_save_success)

    var snackBarState by remember { mutableStateOf(SnackBarState.NONE) }

    LaunchedEffect(key1 = viewModel.uiEffect) {

        viewModel.uiEffect.collect {
            when (it) {
                is AddExpensesUiEffect.ShowError -> {
                    snackBarState = it.snackBarState
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = messageError,
                        duration = SnackbarDuration.Short
                    )
                }
                is AddExpensesUiEffect.ShowSuccess -> {
                    snackBarState = it.snackBarState
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = messageSuccess,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                CustomSnackBar(
                    snackBarState = snackBarState,
                    message = data.message,
                )
            }
        },
        backgroundColor = MyAccountsTheme.colors.background,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(MyAccountsTheme.dimensions.padding16)
                    .verticalScroll(state)
                    .background(MyAccountsTheme.colors.background)
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
                    navigateToHomeScreen = navigateToHomeScreen
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddAccountContent(
    viewModel: AddExpenseViewModel,
    title: String,
    onTitleChange: (String) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onClickSave: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    val isLoading = remember { mutableStateOf(false) }

    var enabled by remember {
        mutableStateOf(false)
    }
    val repeatOptions = stringArrayResource(id = R.array.numbers)
    val dueDateOptions = stringArrayResource(id = R.array.days)
    val messageSaveError = stringResource(id = R.string.expense_save_error)

    var dueDateOptionSelected by remember { mutableStateOf(dueDateOptions[0]) }

    enabled = (viewModel.isEnabledRegisterButton.value && !isLoading.value)

    LaunchedEffect(key1 = viewModel.uiEffect) {
        viewModel.uiEffect.collect {
            when (it) {
                is AddExpensesUiEffect.ShowError -> {
                    isLoading.value = false
                }
                is AddExpensesUiEffect.ShowSuccess -> isLoading.value = false
            }
        }
    }

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
        color = MyAccountsTheme.colors.error,
        fontSize = FontSize.scale12
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing8),
        color = MyAccountsTheme.colors.background
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
        visualTransformation = MaskCurrencyVisualTransformation(currencySymbol)
    )
    Text(
        text = viewModel.valueError.value,
        color = MyAccountsTheme.colors.error,
        fontSize = FontSize.scale12
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing8),
        color = MyAccountsTheme.colors.background
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
        color = MyAccountsTheme.colors.error,
        fontSize = FontSize.scale12
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
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
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.form_text_checkbox),
        isChecked = viewModel.isCheckedPaid.value,
        onCheckedChange = { viewModel.isCheckedPaid.value = it }
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
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
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
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
            modifier = Modifier.height(MyAccountsTheme.dimensions.sizing24),
            color = MyAccountsTheme.colors.background
        )
    }
    LoadingButton(
        onClick = {
            isLoading.value = true
            onClickSave()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(MyAccountsTheme.dimensions.sizing52),
        loading = isLoading.value,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Purple200,
        ),
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.button_add_text),
            fontSize = FontSize.scale16,
        )
    }
    Spacer(modifier = Modifier.height(MyAccountsTheme.dimensions.sizing8))
    LoadingButton(
        onClick = {
            navigateToHomeScreen()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(MyAccountsTheme.dimensions.sizing52),
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Purple200,
        ),
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.button_back_text),
            fontSize = FontSize.scale16,
        )
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}