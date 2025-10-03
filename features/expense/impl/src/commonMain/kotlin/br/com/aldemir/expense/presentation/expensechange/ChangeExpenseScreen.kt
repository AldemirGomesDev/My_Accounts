package br.com.aldemir.expense.presentation.expensechange

import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.Purple200
import br.com.aldemir.common.theme.Purple700
import br.com.aldemir.common.util.MaskCurrencyVisualTransformation
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.toPercentString
import br.com.aldemir.expense.presentation.expensechange.action.ChangeExpenseAction
import br.com.aldemir.expense.presentation.expensechange.effect.ChangeExpenseEffect
import br.com.aldemir.expense.presentation.expensechange.model.ChangeExpenseUiModel
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.button_update
import myaccounts.common.generated.resources.expense_month_and_year
import myaccounts.common.generated.resources.form_add_value
import myaccounts.common.generated.resources.form_invalid_value
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalComposeUiApi
@Composable
fun ChangeExpenseScreen(
    viewModel: ChangeExpenseViewModel = koinViewModel(),
    idMonthlyPayment: Int = -1,
    navigateToDetailScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(idMonthlyPayment) {
        viewModel.sendAction(ChangeExpenseAction.LoadExpense(idMonthlyPayment))
    }

    val uiModel by viewModel.uiModel.collectAsState()

    BackHandler {
        navigateToDetailScreen()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            ChangeExpenseContent(
                uiModel = uiModel,
                paddingValues = padding,
                onValueChange = {
                    viewModel.sendAction(ChangeExpenseAction.OnValueChange(it))
                },
                onClickUpdate = {
                    viewModel.sendAction(
                        ChangeExpenseAction.UpdateMonthlyExpense(idMonthlyPayment)
                    )
                }
            )
        },
    )

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is ChangeExpenseEffect.NavigateBack -> {
                    navigateToDetailScreen()
                }
            }
        }
    }
}

@Composable
private fun ChangeExpenseContent(
    uiModel: ChangeExpenseUiModel,
    paddingValues: PaddingValues,
    onValueChange: (String) -> Unit,
    onClickUpdate: () -> Unit
) {

    val title = stringResource(
        Res.string.expense_month_and_year,
        uiModel.month,
        uiModel.year
    )

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    var enabled by remember {
        mutableStateOf(false)
    }

    val valueString = uiModel.value.toPercentString()

    enabled = (valueString.isNotEmpty() && !uiModel.loading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MyAccountsTheme.colors.background)
            .padding(paddingValues)
            .padding(16.dp)
    ) {

        Text(
            text = title,
            color = Purple700,
            fontWeight = FontWeight.Bold,
        )

        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MyAccountsTheme.colors.background
        )
        InputTextOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = valueString,
            onValueChange = {
                onValueChange(it)
            },
            label = stringResource(Res.string.form_add_value),
            isError = valueString.isEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            visualTransformation = MaskCurrencyVisualTransformation(currencySymbol)
        )

        if (valueString.isEmpty()) Text(
            text = stringResource(Res.string.form_invalid_value),
            color = MaterialTheme.colorScheme.error,
            fontSize = FontSize.scale12
        )
        Divider(
            modifier = Modifier.height(LARGEST_PADDING),
            color = MyAccountsTheme.colors.background
        )

        LoadingButton(
            onClick = {
                onClickUpdate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            loading = uiModel.loading,
            enabled = enabled,
            text = stringResource(Res.string.button_update),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
        )
    }
}
