package br.com.aldemir.recipe.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.backhandler.BackHandler
import br.com.aldemir.common.component.EmptyContent
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.component.DisplayAlertDialog
import br.com.aldemir.common.component.FabAdd
import br.com.aldemir.common.component.StatisticsCard
import br.com.aldemir.common.showMessage
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.recipe.model.RecipeView
import br.com.aldemir.recipe.presentation.list.action.ListRecipeAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.dialog_delete_message
import myaccounts.common.generated.resources.dialog_delete_title
import myaccounts.common.generated.resources.expense_delete_message_toast
import myaccounts.common.generated.resources.recipe_text
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalComposeUiApi
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

    val uiModel by viewModel.uiModel.collectAsState()

    BackHandler {
        navigateToHomeScreen()
    }

    LaunchedEffect(true) {
        viewModel.handleAction(ListRecipeAction.LoadRecipes)
    }

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
                    StatisticsCard(uiModel.cardState)
                }
                if (uiModel.recipes.isNotEmpty()) {
                    LazyColumn(
                        state = state
                    ) {
                        items(
                            items = uiModel.recipes,
                            key = { recipeView ->
                                recipeView.id
                            }
                        ) { recipeView ->
                            RecipeItem(
                                listItems = uiModel.menuItems,
                                recipeView = recipeView,
                                onDelete = {
                                    recipeToSave = recipeView
                                    viewModel.handleAction(ListRecipeAction.ShowDialog(true))

                                },
                                navigateToDetailScreen = { recipeId ->
                                    navigateToDetailScreen(recipeId)
                                },
                            )
                            Divider(
                                modifier = Modifier.height(0.5.dp),
                                color = MaterialTheme.colorScheme.dividerColor
                            )
                        }
                    }
                } else EmptyContent(text = stringResource(Res.string.recipe_text))

                val message = stringResource(Res.string.expense_delete_message_toast)
                DisplayAlertDialog(
                    title = stringResource(Res.string.dialog_delete_title),
                    message = stringResource(Res.string.dialog_delete_message),
                    openDialog = uiModel.showDialog,
                    closeDialog = {
                        viewModel.handleAction(ListRecipeAction.ShowDialog(false))
                    },
                    onYesClicked = {
                        deleteExpense(viewModel, recipeToSave)
                        showToast(message)
                        viewModel.handleAction(ListRecipeAction.ShowDialog(false))
                    }
                )
            }
        },
        floatingActionButton = {
            FabAdd(onFabClicked = navigateToAddRecipeScreen)
        }
    )
}

private fun showToast(message: String) {
    showMessage(message)
}

private fun deleteExpense(viewModel: ListRecipeViewModel, recipe: RecipeView) {
    CoroutineScope(Dispatchers.Default).launch {
        viewModel.handleAction(ListRecipeAction.DeleteRecipe(recipe))
        delay(300)
    }
}