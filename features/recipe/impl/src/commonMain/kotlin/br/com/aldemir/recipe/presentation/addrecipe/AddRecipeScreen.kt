package br.com.aldemir.recipe.presentation.addrecipe


import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.component.CheckboxWithText
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.component.MyExposedDropdownMenu
import br.com.aldemir.common.theme.*
import br.com.aldemir.common.util.MaskCurrencyVisualTransformation
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.recipe.presentation.addrecipe.action.AddRecipeAction
import br.com.aldemir.recipe.presentation.addrecipe.effect.AddRecipeEffect
import br.com.aldemir.recipe.presentation.addrecipe.model.AddRecipeUiModel
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.button_add_text
import myaccounts.common.generated.resources.days
import myaccounts.common.generated.resources.form_add_description
import myaccounts.common.generated.resources.form_add_name
import myaccounts.common.generated.resources.form_add_value
import myaccounts.common.generated.resources.form_due_date_day
import myaccounts.common.generated.resources.form_how_many_times_repeat
import myaccounts.common.generated.resources.form_text_checkbox
import myaccounts.common.generated.resources.form_text_checkbox_repeat
import myaccounts.common.generated.resources.numbers
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AddRecipeScreen(
    viewModel: AddRecipeViewModel = koinViewModel(),
    navigateToListRecipeScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val focusManager = LocalFocusManager.current

    val handleAction = viewModel::handleAction

    val uiModel by viewModel.uiModel.collectAsState()

    BackHandler {
        navigateToListRecipeScreen()
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
                    .verticalScroll(state)
                    .background(MyAccountsTheme.colors.background)
                    .padding(16.dp)
            ) {
                AddAccountContent(
                    uiModel = uiModel,
                    handleAction = handleAction,
                    onClickSave = {
                        handleAction(AddRecipeAction.SaveRecipe)
                        focusManager.clearFocus()
                    },
                )
            }
        },
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is AddRecipeEffect.RecipeSaved -> {
                    navigateToListRecipeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddAccountContent(
    uiModel: AddRecipeUiModel,
    handleAction: (AddRecipeAction) -> Unit,
    onClickSave: () -> Unit
) {
    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    val isLoading = remember { mutableStateOf(false) }

    var enabled by remember {
        mutableStateOf(false)
    }
    val repeatOptions = stringArrayResource(Res.array.numbers)
    val dueDateOptions = stringArrayResource(Res.array.days)

    var dueDateOptionSelected by remember { mutableStateOf(dueDateOptions[0]) }

    enabled = (uiModel.isEnabledRegisterButton && !isLoading.value)

    InputTextOutlinedTextField(
        value = uiModel.name,
        onValueChange = {
            handleAction(AddRecipeAction.SetName(it))
        },
        label = stringResource(Res.string.form_add_name),
        isError = uiModel.isNameValid
    )
    Text(
        text = uiModel.nameError,
        color = MaterialTheme.colors.error,
        fontSize = FontSize.scale12
    )
    Divider(
        modifier = Modifier.height(MEDIUM_PADDING),
        color = MyAccountsTheme.colors.background
    )
    InputTextOutlinedTextField(
        value = uiModel.value,
        onValueChange = {
            handleAction(AddRecipeAction.SetValue(it))
        },
        label = stringResource(Res.string.form_add_value),
        isError = uiModel.isValueValid,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next,
        ),
        visualTransformation = MaskCurrencyVisualTransformation(currencySymbol)
    )
    Text(
        text = uiModel.valueError,
        color = MaterialTheme.colors.error,
        fontSize = FontSize.scale12
    )
    Divider(
        modifier = Modifier.height(MEDIUM_PADDING),
        color = MyAccountsTheme.colors.background
    )
    InputTextOutlinedTextField(
        value = uiModel.description,
        onValueChange = {
            handleAction(AddRecipeAction.SetDescription(it))
        },
        label = stringResource(Res.string.form_add_description),
        isError = uiModel.isDescriptionValid,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        )
    )
    Text(
        text = uiModel.descriptionError,
        color = MaterialTheme.colors.error,
        fontSize = FontSize.scale12
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MyAccountsTheme.colors.background
    )
    MyExposedDropdownMenu(
        label = stringResource( Res.string.form_due_date_day),
        listItems = dueDateOptions.toList(),
        selected = dueDateOptionSelected,
        onItemSelected = { item ->
            dueDateOptionSelected = item
            handleAction(AddRecipeAction.SetDueDateSelected(item.toInt()))
        },
        modifier = Modifier.fillMaxWidth()
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MyAccountsTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(Res.string.form_text_checkbox),
        isChecked = uiModel.isCheckedPaid,
        onCheckedChange = {
            handleAction(AddRecipeAction.SetIsCheckedPaid(it))
        }
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MyAccountsTheme.colors.background
    )
    CheckboxWithText(
        text = stringResource(Res.string.form_text_checkbox_repeat),
        isChecked = uiModel.isAccountRepeat,
        onCheckedChange = {
            handleAction(AddRecipeAction.SetIsAccountRepeat(it))
        }
    )
    Divider(
        modifier = Modifier.height(SMALL_PADDING),
        color = MyAccountsTheme.colors.background
    )
    if (uiModel.isAccountRepeat) {
        MyExposedDropdownMenu(
            label = stringResource(Res.string.form_how_many_times_repeat),
            listItems = repeatOptions.toList(),
            selected = uiModel.amountThatRepeatsSelected.toString(),
            onItemSelected = { item ->
                handleAction(AddRecipeAction.SetAmountThatRepeatsSelected(item.toInt()))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Divider(
            modifier = Modifier.height(LARGEST_PADDING),
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
            .height(52.dp),
        loading = isLoading.value,
        enabled = enabled,
        text = stringResource(Res.string.button_add_text)
    )
}