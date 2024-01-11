package br.com.aldemir.recipe.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.Purple700
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.TASK_ITEM_ELEVATION
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.theme.taskItemBackgroundColor
import br.com.aldemir.common.R
import br.com.aldemir.common.component.DisplayAlertDialog
import br.com.aldemir.common.component.MyDropdownMenuItem
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.recipe.model.RecipeMonthlyView
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailRecipeScreen(
    navigateToChangeScreen: (recipeId: Int) -> Unit,
    navigateToBackScreen: () -> Unit,
    viewModel: DetailRecipeViewModel = koinViewModel(),
    recipeId: Int = -1,
) {
    val scaffoldState = rememberScaffoldState()

    val id by viewModel.id.observeAsState()

    LaunchedEffect(key1 = recipeId) {
        viewModel.getAllByIdRecipeMonthly(recipeId)
    }

    var monthlyPaymentToUpdate by remember {
        mutableStateOf(RecipeMonthlyView())
    }

    id?.let {
        if (it > 0) viewModel.getAllByIdRecipeMonthly(recipeId)
    }

    val recipeMonthlyViews by viewModel.recipeMonthlyView.collectAsState()

    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    val name by viewModel.name.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                TextTitleLarge(
                    text = name?: emptyString(),
                    color = Purple700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = LARGE_PADDING, vertical = MEDIUM_PADDING)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Divider(
                    modifier = Modifier.height(MEDIUM_PADDING),
                    color = MaterialTheme.colors.background
                )
                DetailRecipeList(
                    navigateToChangeScreen = navigateToChangeScreen,
                    recipesMonthly = recipeMonthlyViews,
                    onClickUpdate = { index, recipeMonthlyView ->
                        monthlyPaymentToUpdate = recipeMonthlyView.copy(status = true)
                        viewModel.onOpenDialogClicked()
                    },
                    viewModel = viewModel
                )
                DisplayAlertDialog(
                    title = stringResource(id = R.string.dialog_confirm_alert_title),
                    message = stringResource(id = R.string.dialog_confirm_recipe_message),
                    openDialog = showDialogState,
                    closeDialog = {
                        viewModel.onDialogDismiss()
                    },
                    onYesClicked = {
                        viewModel.updateRecipeMonthly(monthlyPaymentToUpdate)
                        viewModel.onDialogConfirm()
                    }
                )
            }
        }
    )
}

@Composable
private fun DetailRecipeList(
    navigateToChangeScreen: (idRecipe: Int) -> Unit,
    recipesMonthly: List<RecipeMonthlyView>,
    onClickUpdate: (Int, RecipeMonthlyView) -> Unit,
    viewModel: DetailRecipeViewModel
) {
    val state = rememberLazyListState()

    LazyColumn(state = state) {
        itemsIndexed(
            items = recipesMonthly,
        ) { index, recipeMonthly ->
            DetailRecipeItem(
                navigateToChangeScreen = navigateToChangeScreen,
                recipeMonthlyView = recipeMonthly,
                onClickUpdate = onClickUpdate,
                viewModel = viewModel,
                index = index
            )
        }
    }
}

@Composable
private fun DetailRecipeItem(
    navigateToChangeScreen: (recipeId: Int) -> Unit,
    recipeMonthlyView: RecipeMonthlyView,
    onClickUpdate: (Int, RecipeMonthlyView) -> Unit,
    viewModel: DetailRecipeViewModel,
    index: Int,
) {
    LaunchedEffect(key1 = true){
        viewModel.getItemsMenu()
    }
    val context = LocalContext.current

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    val statusColor = viewModel.getStatusColor(recipeMonthlyView.status, recipeMonthlyView.expired)
    val resourceId = viewModel.getStatusText(recipeMonthlyView.status, recipeMonthlyView.expired)

    val disabledItem by remember {
        mutableStateOf(!recipeMonthlyView.status)
    }

    val menuItems by viewModel.menuItemsState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MaterialTheme.colors.taskItemBackgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (recipeMonthlyView.status)
                                viewModel.showToast(
                                    context,
                                    context.getString(R.string.expense_no_update_message_toast)
                                )
                            else navigateToChangeScreen(recipeMonthlyView.id)
                        },
                    )
                },
        ) {
            TextTitleItem(
                text = "${recipeMonthlyView.year} - ${recipeMonthlyView.month}",
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextSubTitleItem(text = stringResource(id = R.string.account_label_value))
                TextBodyTwoItem(
                    text = recipeMonthlyView.value.toCurrency(currencySymbol),
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
                TextBodyTwoItem(
                    text = " - ",
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
                TextBodyTwoItem(
                    modifier = Modifier.padding(start = SMALL_PADDING),
                    text = stringResource(id = resourceId),
                    color = statusColor,
                )
                Spacer(modifier = Modifier.weight(1f))

                MyDropdownMenuItem(
                    onItemClicked = { type ->
                        when(type) {
                            DropdownItemType.PAY -> { onClickUpdate(index, recipeMonthlyView) }
                            DropdownItemType.DELETE -> { }
                            else -> {}
                        }
                    },
                    listItems = menuItems,
                    disabledItem = disabledItem
                )
            }
            Divider(
                modifier = Modifier.height(0.5.dp),
                color = MaterialTheme.colors.dividerColor
            )
        }
    }
}