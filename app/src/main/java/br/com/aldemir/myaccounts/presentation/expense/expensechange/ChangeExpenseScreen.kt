package br.com.aldemir.myaccounts.presentation.expense.expensechange

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.presentation.component.LoadingButton
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.*

@Composable
fun ChangeExpenseScreen(
    viewModel: ChangeExpenseViewModel = hiltViewModel(),
    idMonthlyPayment: Int = -1,
    expenseName: String = emptyString(),
    navigateToDetailScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    viewModel.getAllByIdMonthlyPayment(idMonthlyPayment)

    val initValue: String by viewModel.value

    val mIdMonthlyPayment: Int by viewModel.idMonthlyPayment.collectAsState()

    val mMonthlyPayments = remember { mutableStateOf(MonthlyPayment()) }

    val monthlyPayments by viewModel.monthlyPayment.collectAsState()

    mMonthlyPayments.value = monthlyPayments

    val title = context.getString(
        R.string.expense_month_and_year,
        mMonthlyPayments.value.year,
        mMonthlyPayments.value.month
    )

    viewModel.value.value =
        viewModel.getValueWithTwoDecimal(mMonthlyPayments.value.value.toString())

    LaunchedEffect(key1 = mIdMonthlyPayment) {
        if (mIdMonthlyPayment > 0) navigateToDetailScreen()
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

    var loading by remember {
        mutableStateOf(false)
    }

    var enabled by remember {
        mutableStateOf(false)
    }

    enabled = (value.isNotEmpty() && !loading)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = title,
            color = Purple700,
            fontWeight = FontWeight.Bold,
        )

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
            trailingIcon = {
                if (value.isEmpty()) Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = emptyString()
                )
            },
        )
        if (value.isEmpty()) Text(
            text = stringResource(id = R.string.invalid_value),
            color = MaterialTheme.colors.error,
            fontSize = FONT_SIZE_12
        )
        Divider(
            modifier = Modifier.height(LARGEST_PADDING),
            color = MaterialTheme.colors.background
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
                text = stringResource(id = R.string.expense_update_button),
                fontSize = FONT_SIZE_16,
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