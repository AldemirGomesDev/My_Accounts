package br.com.aldemir.expense.presentation.expensechange

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.Purple200
import br.com.aldemir.common.theme.Purple700
import br.com.aldemir.common.theme.addAccountBorderColor
import br.com.aldemir.common.theme.addAccountLabelColor
import br.com.aldemir.common.util.MaskCurrencyVisualTransformation
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.R
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangeExpenseScreen(
    viewModel: ChangeExpenseViewModel = koinViewModel(),
    idMonthlyPayment: Int = -1,
    expenseName: String = emptyString(),
    navigateToDetailScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    viewModel.getAllByIdMonthlyPayment(idMonthlyPayment)

    val initValue: String by viewModel.value

    val mIdMonthlyPayment: Int by viewModel.idMonthlyPayment.collectAsState()

    val mExpenseMonthlyPayments = remember { mutableStateOf(ExpenseMonthlyDomain()) }

    val monthlyPayments by viewModel.expenseMonthlyDomain.collectAsState()

    mExpenseMonthlyPayments.value = monthlyPayments

    val title = context.getString(
        R.string.expense_month_and_year,
        mExpenseMonthlyPayments.value.year,
        mExpenseMonthlyPayments.value.month
    )

    viewModel.value.value =
        viewModel.getValueWithTwoDecimal(mExpenseMonthlyPayments.value.value.toString())

    LaunchedEffect(key1 = mIdMonthlyPayment) {
        if (mIdMonthlyPayment > 0) navigateToDetailScreen()
    }

    BackHandler {
        navigateToDetailScreen()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            ChangeExpenseContent(
                title = title,
                value = initValue,
                paddingValues = padding,
                onValueChange = {
                    viewModel.value.value = it
                },
                onClickUpdate = {
                    viewModel.updateMonthlyPayment()
                }
            )
        },
    )

}

@Composable
private fun ChangeExpenseContent(
    title: String,
    value: String,
    paddingValues: PaddingValues,
    onValueChange: (String) -> Unit,
    onClickUpdate: () -> Unit
) {

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    var loading by remember {
        mutableStateOf(false)
    }

    var enabled by remember {
        mutableStateOf(false)
    }

    enabled = (value.isNotEmpty() && !loading)

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

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = stringResource(R.string.form_add_value)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = MaskCurrencyVisualTransformation(currencySymbol),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
                unfocusedBorderColor = MaterialTheme.colors.addAccountBorderColor,
                focusedLabelColor = MaterialTheme.colors.addAccountBorderColor,
                unfocusedLabelColor = MaterialTheme.colors.addAccountLabelColor,
                textColor = MaterialTheme.colors.addAccountBorderColor,
                disabledTextColor = MaterialTheme.colors.addAccountBorderColor
            ),
            isError = value.isEmpty(),
            trailingIcon = {
                if (value.isEmpty()) Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = emptyString()
                )
            },
        )
        if (value.isEmpty()) Text(
            text = stringResource(id = R.string.form_invalid_value),
            color = MaterialTheme.colors.error,
            fontSize = FontSize.scale12
        )
        Divider(
            modifier = Modifier.height(LARGEST_PADDING),
            color = MyAccountsTheme.colors.background
        )

        LoadingButton(
            onClick = {
                loading = true
                onClickUpdate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            loading = loading,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.button_update),
                fontSize = FontSize.scale16,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeExpenseContentPreview() {
    ChangeExpenseContent(
        "12 - Dezembro",
        "",
        paddingValues = PaddingValues(),
        onValueChange = {},
        onClickUpdate = {}
    )
}