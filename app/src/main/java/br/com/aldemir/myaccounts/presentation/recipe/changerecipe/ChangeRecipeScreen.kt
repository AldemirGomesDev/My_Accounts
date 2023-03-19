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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.model.RecipePerMonthDTO
import br.com.aldemir.myaccounts.presentation.component.CheckboxWithText
import br.com.aldemir.myaccounts.presentation.component.InputTextOutlinedTextField
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
    val name: String by viewModel.name
    val description: String by viewModel.description

    val mIdMonthlyRecipe: Int by viewModel.idMonthlyRecipe.collectAsState()

    val mMonthlyRecipes = remember { mutableStateOf(RecipePerMonthDTO()) }

    val monthlyRecipes by viewModel.recipeMonthlyView.collectAsState()

    mMonthlyRecipes.value = monthlyRecipes

    val yearAndMonth = context.getString(
        R.string.expense_month_and_year,
        mMonthlyRecipes.value.year,
        mMonthlyRecipes.value.month
    )

    viewModel.value.value =
        viewModel.getValueWithTwoDecimal(mMonthlyRecipes.value.value.toString())

    viewModel.recipeId.value = mMonthlyRecipes.value.id_recipe
    viewModel.name.value = mMonthlyRecipes.value.name
    viewModel.description.value = mMonthlyRecipes.value.description
    viewModel.isCheckedPaid.value = mMonthlyRecipes.value.status

    LaunchedEffect(key1 = mIdMonthlyRecipe) {
        if (mIdMonthlyRecipe > 0) navigateToDetailScreen()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            ChangeRecipeContent(
                yearAndMonth = yearAndMonth,
                title = name,
                onTitleChange = {
                    viewModel.name.value = it
                },
                value = initValue,
                onValueChange = {
                    viewModel.value.value = it
                },
                description = description,
                onDescriptionChange = {
                    viewModel.description.value = it
                },
                onClickUpdate = {
                    viewModel.updateMonthlyRecipe()
                },
                paddingValues = padding,
                viewModel = viewModel
            )
        },
    )

}

@Composable
private fun ChangeRecipeContent(
    yearAndMonth: String,
    title: String,
    onTitleChange: (String) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onClickUpdate: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: ChangeRecipeViewModel
) {

    var loading by remember {
        mutableStateOf(false)
    }

    var enabled by remember {
        mutableStateOf(false)
    }

    enabled = (viewModel.isEnabledRegisterButton.value && !loading)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = yearAndMonth,
            color = Purple700,
            fontWeight = FontWeight.Bold,
        )

        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )

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

        CheckboxWithText(
            text = stringResource(id = R.string.form_text_checkbox),
            isChecked = viewModel.isCheckedPaid.value,
            onCheckedChange = { viewModel.isCheckedPaid.value = it }
        )
        Divider(
            modifier = Modifier.height(SMALL_PADDING),
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
        yearAndMonth = emptyString(),
        title = "12 - Dezembro",
        onTitleChange = {},
        value = "90",
        onValueChange = {},
        description = "",
        onDescriptionChange = {},
        paddingValues = PaddingValues(),
        onClickUpdate = {},
        viewModel = hiltViewModel(),
    )
}