package br.com.aldemir.expense.presentation.addexpense

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
import br.com.aldemir.expense.presentation.addexpense.action.AddExpenseAction
import br.com.aldemir.expense.presentation.addexpense.state.AddExpenseUiState
import org.koin.androidx.compose.koinViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddExpenseScreen(
    viewModel: AddExpenseViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsState()

    BackHandler {
        navigateToBack()
    }

    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(10) }

    val messageError = stringResource(id = R.string.expense_save_error)
    val messageSuccess = stringResource(id = R.string.expense_save_success)

    var snackBarState by remember { mutableStateOf(SnackBarState.NONE) }
    var uiEffect by remember { mutableStateOf<AddExpensesUiEffect>(AddExpensesUiEffect.Idle) }

    LaunchedEffect(key1 = viewModel.uiEffect) {

        viewModel.uiEffect.collect {
            uiEffect = it
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
                    viewModel.onAction(AddExpenseAction.ClearForm)
                } else -> {}
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
                    uiEffect = uiEffect,
                    uiState = uiState,
                    title = uiState.name,
                    onTitleChange = {
                        viewModel.onAction(AddExpenseAction.NameChanged(it))
                    },
                    value = uiState.value,
                    onValueChange = {
                        viewModel.onAction(AddExpenseAction.ValueChanged(it))
                    },
                    description = uiState.description,
                    onDescriptionChange = {
                        viewModel.onAction(AddExpenseAction.DescriptionChanged(it))
                    },
                    onClickSave = {
                        viewModel.onAction(AddExpenseAction.Submit)
                        focusManager.clearFocus()
                    },
                    navigateToHomeScreen = navigateToBack,
                    onCheckedChangeMonth = {
                        viewModel.onAction(AddExpenseAction.CheckedPaidChanged(it))
                    },
                    onCheckedChangeRepeat = {
                        viewModel.onAction(AddExpenseAction.AccountRepeatChanged(it))
                        viewModel.clearRepeatAmount(it)
                    },
                    onItemSelectedMonth = {
                        viewModel.onAction(AddExpenseAction.DueDateSelectedChanged(it))
                    },
                    onItemSelectedRepeat = {
                        viewModel.onAction(AddExpenseAction.AmountThatRepeatsSelectedChanged(it))
                    }
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddAccountContent(
    uiEffect: AddExpensesUiEffect,
    uiState: AddExpenseUiState,
    title: String,
    onTitleChange: (String) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onClickSave: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    onCheckedChangeMonth: (Boolean) -> Unit,
    onCheckedChangeRepeat: (Boolean) -> Unit,
    onItemSelectedMonth: (String) -> Unit,
    onItemSelectedRepeat: (String) -> Unit
) {

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    val isLoading = remember { mutableStateOf(false) }

    var enabled by remember {
        mutableStateOf(false)
    }
    val repeatOptions = stringArrayResource(id = R.array.numbers)
    val dueDateOptions = stringArrayResource(id = R.array.days)

    var dueDateOptionSelected by remember { mutableStateOf(dueDateOptions[0]) }

    enabled = (uiState.isEnabledRegisterButton && !isLoading.value)

    when (uiEffect) {
        is AddExpensesUiEffect.ShowError -> {
            isLoading.value = false
        }
        is AddExpensesUiEffect.ShowSuccess -> isLoading.value = false
        else -> {}
    }

    InputTextOutlinedTextField(
        value = title,
        onValueChange = {
            onTitleChange(it)
        },
        label = stringResource(R.string.form_add_name),
        isError = uiState.isNameValid
    )
    Text(
        text = uiState.nameError,
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
        },
        label = stringResource(R.string.form_add_value),
        isError = uiState.isValueValid,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next,
        ),
        visualTransformation = MaskCurrencyVisualTransformation(currencySymbol)
    )
    Text(
        text = uiState.valueError,
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
        },
        label = stringResource(R.string.form_add_description),
        isError = uiState.isDescriptionValid,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Text(
        text = uiState.descriptionError,
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
            onItemSelectedMonth(item)
        },
        modifier = Modifier.fillMaxWidth()
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.form_text_checkbox),
        isChecked = uiState.isCheckedPaid,
        onCheckedChange = { onCheckedChangeMonth(it) }
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(id = R.string.form_text_checkbox_repeat),
        isChecked = uiState.isAccountRepeat,
        onCheckedChange = { onCheckedChangeRepeat(it) }
    )
    Divider(
        modifier = Modifier.height(MyAccountsTheme.dimensions.sizing4),
        color = MyAccountsTheme.colors.background
    )
    if (uiState.isAccountRepeat) {
        MyExposedDropdownMenu(
            label = stringResource(id = R.string.form_how_many_times_repeat),
            listItems = repeatOptions.toList(),
            selected = uiState.amountThatRepeatsSelected.toString(),
            onItemSelected = { item ->
                onItemSelectedRepeat(item)
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