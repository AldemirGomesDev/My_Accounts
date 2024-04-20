package br.com.aldemir.recipe.presentation.list

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.component.EmptyContent
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.R
import br.com.aldemir.common.component.DisplayAlertDialog
import br.com.aldemir.common.component.FabAdd
import br.com.aldemir.common.component.StatisticsCard
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.recipe.model.RecipeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterialApi
@Composable
fun ListRecipeScreen(
    viewModel: ListRecipeViewModel = koinViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToAddRecipeScreen: () -> Unit,
    navigateToDetailScreen: (recipeId: Int) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val state = rememberLazyListState()

    var recipeToSave by remember {
        mutableStateOf(RecipeView())
    }

    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    val context = LocalContext.current

    BackHandler {
        navigateToHomeScreen()
    }

    LaunchedEffect(true) {
        viewModel.getItemsMenu()
        viewModel.getAllRecipePerMonth(DateUtils.getMonth(), DateUtils.getYear())
        viewModel.getAllRecipeMonthly(DateUtils.getMonth(), DateUtils.getYear())
    }

    val recipes by viewModel.recipes.observeAsState()
    val cardState by viewModel.cardState.collectAsState()
    val menuItems by viewModel.menuItemsState.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MyAccountsTheme.colors.background),
            ) {
                Box(modifier = Modifier.padding(horizontal = MyAccountsTheme.dimensions.padding16)) {
                    StatisticsCard(cardState)
                }
                recipes?.let {
                    if (it.isNotEmpty()) {
                        LazyColumn(
                            state = state
                        ) {
                            items(
                                items = it,
                                key = { recipeView ->
                                    recipeView.id
                                }
                            ) { recipeView ->
                                RecipeItem(
                                    listItems = menuItems,
                                    recipeView = recipeView,
                                    viewModel = viewModel,
                                    onDelete = {
                                        recipeToSave = recipeView
                                        viewModel.onOpenDialogClicked()
                                    },
                                    navigateToDetailScreen = { recipeId ->
                                        navigateToDetailScreen(recipeId)
                                    },
                                )
                                Divider(
                                    modifier = Modifier.height(0.5.dp),
                                    color = MaterialTheme.colors.dividerColor
                                )
                            }
                        }
                    } else EmptyContent(text = stringResource(id = R.string.recipe_text))
                } ?: run {
                    EmptyContent()
                }
                DisplayAlertDialog(
                    title = stringResource(id = R.string.dialog_delete_title),
                    message = stringResource(id = R.string.dialog_delete_message),
                    openDialog = showDialogState,
                    closeDialog = {
                        viewModel.onDialogDismiss()
                    },
                    onYesClicked = {
                        deleteExpense(viewModel, recipeToSave)
                        showToast(
                            context,
                            context.getString(
                                R.string.expense_delete_message_toast,
                                recipeToSave.id
                            )
                        )
                        viewModel.onDialogConfirm()
                    }
                )
            }
        },
        floatingActionButton = {
            FabAdd(onFabClicked = navigateToAddRecipeScreen)
        }
    )
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

private fun deleteExpense(viewModel: ListRecipeViewModel, recipe: RecipeView) {
    CoroutineScope(Dispatchers.Default).launch {
        viewModel.delete(recipe)
        delay(300)
        getAllExpenseMonth(viewModel)
    }
}

private fun getAllExpenseMonth(viewModel: ListRecipeViewModel) {
    viewModel.getAllRecipePerMonth(DateUtils.getMonth(), DateUtils.getYear())
}