package br.com.aldemir.myaccounts.presentation.recipe.changerecipe

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
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO
import br.com.aldemir.myaccounts.presentation.component.LoadingButton
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.*

@Composable
fun ChangeRecipeScreen(
    viewModel: ChangeRecipeViewModel = hiltViewModel(),
    idMonthlyRecipe: Int = -1,
    navigateToDetailScreen: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getAllByIdMonthlyRecipe(idMonthlyRecipe)
    }

    val initValue: String by viewModel.value

    val mIdMonthlyRecipe: Int by viewModel.idMonthlyRecipe.collectAsState()

    val mMonthlyRecipes = remember { mutableStateOf(RecipePerMonthDTO()) }

    val monthlyRecipes by viewModel.recipeMonthlyView.collectAsState()

    mMonthlyRecipes.value = monthlyRecipes

    val title = context.getString(
        R.string.expense_month_and_year,
        mMonthlyRecipes.value.year,
        mMonthlyRecipes.value.month
    )

    viewModel.value.value =
        viewModel.getValueWithTwoDecimal(mMonthlyRecipes.value.value.toString())

    LaunchedEffect(key1 = mIdMonthlyRecipe) {
        if (mIdMonthlyRecipe > 0) navigateToDetailScreen()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            ChangeRecipeContent(
                title = title,
                value = initValue,
                paddingValues = padding,
                onValueChange = {
                    viewModel.value.value = it
                },
                onClickUpdate = {
                    viewModel.updateMonthlyRecipe()
                }
            )
        },
    )

}

@Composable
private fun ChangeRecipeContent(
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
            text = stringResource(id = R.string.form_invalid_value),
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
                text = stringResource(id = R.string.button_update),
                fontSize = FONT_SIZE_16,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeRecipeContentPreview() {
    ChangeRecipeContent(
        "12 - Dezembro",
        "",
        paddingValues = PaddingValues(),
        onValueChange = {},
        onClickUpdate = {}
    )
}