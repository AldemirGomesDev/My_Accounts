package br.com.aldemir.myaccounts.presentation.recipe.detail

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.*
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeMonthlyView
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.toCurrency

@Composable
fun DetailRecipeScreen(
    navigateToChangeScreen: (recipeId: Int) -> Unit,
    navigateToBackScreen: () -> Unit,
    viewModel: DetailRecipeViewModel = hiltViewModel(),
    recipeId: Int = -1,
    recipeName: String = emptyString()
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

    Scaffold(
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                TextTitleLarge(
                    text = recipeName,
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
                    title = "Aviso",
                    message = "Deseja confirmar o recebimento?",
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
    val context = LocalContext.current

    val statusColor = viewModel.getStatusColor(recipeMonthlyView.status, recipeMonthlyView.expired)
    val resourceId = viewModel.getStatusText(recipeMonthlyView.status, recipeMonthlyView.expired)

    val buttonAlpha by animateFloatAsState(targetValue = if (recipeMonthlyView.status) 0f else 1f)

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
                                    context.getString(R.string.no_update_message_toast)
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
                TextSubTitleItem(text = stringResource(id = R.string.label_value))
                TextBodyTwoItem(
                    text = recipeMonthlyView.value.toCurrency(),
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
                OutlinedButton(
                    enabled = !recipeMonthlyView.status,
                    modifier = Modifier.alpha(buttonAlpha),
                    onClick = {
                        onClickUpdate(index, recipeMonthlyView)
                    }
                ) {
                    TextMyButton(text = stringResource(id = R.string.button_text_pay))
                }
            }
            Divider(
                modifier = Modifier.height(0.5.dp),
                color = MaterialTheme.colors.dividerColor
            )
        }
    }
}