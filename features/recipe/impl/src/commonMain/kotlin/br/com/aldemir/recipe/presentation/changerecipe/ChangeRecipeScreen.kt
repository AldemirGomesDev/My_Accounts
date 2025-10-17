package br.com.aldemir.recipe.presentation.changerecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.Purple200
import br.com.aldemir.common.theme.Purple700
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.util.MaskCurrencyVisualTransformation
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.component.CheckboxWithText
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.toPercentString
import br.com.aldemir.recipe.presentation.changerecipe.action.ChangeRecipeAction
import br.com.aldemir.recipe.presentation.changerecipe.effect.ChangeRecipeEffect
import br.com.aldemir.recipe.presentation.changerecipe.model.ChangeRecipeUiModel
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.button_update
import myaccounts.common.generated.resources.expense_month_and_year
import myaccounts.common.generated.resources.form_add_description
import myaccounts.common.generated.resources.form_add_name
import myaccounts.common.generated.resources.form_add_value
import myaccounts.common.generated.resources.form_text_checkbox
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChangeRecipeScreen(
    viewModel: ChangeRecipeViewModel = koinViewModel(),
    idMonthlyRecipe: Int = -1,
    navigateToDetailScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.getAllByIdMonthlyRecipe(idMonthlyRecipe)
    }

    val uiModel by viewModel.uiModel.collectAsState()


    val yearAndMonth = stringResource(
        Res.string.expense_month_and_year,
        uiModel.year,
        uiModel.month
    )

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            ChangeRecipeContent(
                yearAndMonth = yearAndMonth,
                title = uiModel.name,
                onClickUpdate = { isPaid ->
                    viewModel.handleAction(
                        ChangeRecipeAction.UpdateMonthlyRecipe(idMonthlyRecipe, isPaid)
                    )
                },
                sendAction = { action ->
                    viewModel.handleAction(action)
                },
                paddingValues = padding,
                uiModel = uiModel
            )
        },
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ChangeRecipeEffect.NavigateToRecipeList -> {
                    navigateToDetailScreen()
                }
            }
        }
    }

}

@Composable
private fun ChangeRecipeContent(
    yearAndMonth: String,
    title: String,
    onClickUpdate: (isPaid: Boolean) -> Unit,
    sendAction: (ChangeRecipeAction) -> Unit,
    paddingValues: PaddingValues,
    uiModel: ChangeRecipeUiModel
) {
    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    var loading by remember {
        mutableStateOf(false)
    }

    var enabled by remember {
        mutableStateOf(false)
    }

    val valueString = uiModel.value.toPercentString()

    enabled = (uiModel.isEnabledRegisterButton && !loading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MyAccountsTheme.colors.background)
            .padding(16.dp)
    ) {

        Text(
            text = yearAndMonth,
            color = Purple700,
            fontWeight = FontWeight.Bold,
        )

        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MyAccountsTheme.colors.background
        )

        InputTextOutlinedTextField(
            value = title,
            onValueChange = {
                sendAction(ChangeRecipeAction.OnTitleChange(it))
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
            value = valueString,
            onValueChange = {
                sendAction(ChangeRecipeAction.OnValueChange(it))
            },
            label = stringResource(Res.string.form_add_value),
            isError = valueString.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            visualTransformation = MaskCurrencyVisualTransformation(currencySymbol)
        )
        if (valueString.isEmpty()) {
            Text(
                text = uiModel.valueError,
                color = MaterialTheme.colors.error,
                fontSize = FontSize.scale12
            )
        }

        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MyAccountsTheme.colors.background
        )

        InputTextOutlinedTextField(
            value = uiModel.description,
            onValueChange = {
                sendAction(ChangeRecipeAction.OnDescriptionChange(it))
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

        CheckboxWithText(
            text = stringResource(Res.string.form_text_checkbox),
            isChecked = uiModel.isCheckedPaid,
            onCheckedChange = {
                sendAction(ChangeRecipeAction.OnCheckedChange(it))
            }
        )
        Divider(
            modifier = Modifier.height(SMALL_PADDING),
            color = MyAccountsTheme.colors.background
        )

        LoadingButton(
            onClick = {
                loading = true
                onClickUpdate(uiModel.isCheckedPaid)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            loading = loading,
            enabled = enabled,
            text = stringResource(Res.string.button_update),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
        )

    }
}
