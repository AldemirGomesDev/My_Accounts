package br.com.aldemir.expense.presentation.listexpense

import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
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
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.component.DisplayAlertDialog
import br.com.aldemir.common.component.EmptyContent
import br.com.aldemir.common.component.FabAdd
import br.com.aldemir.common.component.StatisticsCard
import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.showMessage
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.expense.model.ExpenseView
import br.com.aldemir.expense.presentation.listexpense.action.ExpenseUiAction
import br.com.aldemir.expense.presentation.listexpense.model.ExpenseUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.dialog_delete_message
import myaccounts.common.generated.resources.dialog_delete_title
import myaccounts.common.generated.resources.expense_delete_message_toast
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun ListExpenseScreen(
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToAddScreen: () -> Unit,
    viewModel: ListExpenseViewModel = koinViewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    val uiModel by viewModel.uiState.collectAsState()

    var expenseDTOToSave by remember {
        mutableStateOf(ExpenseView())
    }

    BackHandler {
        navigateToHomeScreen()
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
                HomeCard(viewModel = viewModel, uiModel = uiModel)
                HomeScreenList(
                    navigateToTaskScreen = navigateToTaskScreen,
                    onDelete = { expense ->
                        expenseDTOToSave = expense
                        viewModel.sendAction(ExpenseUiAction.ShowDialog(true))
                    },
                    viewModel = viewModel
                )
                val message = stringResource(Res.string.expense_delete_message_toast, expenseDTOToSave.id)
                DisplayAlertDialog(
                    title = stringResource(Res.string.dialog_delete_title),
                    message = stringResource(Res.string.dialog_delete_message),
                    openDialog = uiModel.showDialog,
                    closeDialog = {
                        viewModel.sendAction(ExpenseUiAction.ShowDialog(false))
                    },
                    onYesClicked = {
                        deleteExpense(viewModel, expenseDTOToSave)
                        showMessage(message)
                        viewModel.sendAction(ExpenseUiAction.ShowDialog(false))
                    }
                )
            }
        },
        floatingActionButton = {
            FabAdd(onFabClicked = navigateToAddScreen)
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreenList(
    viewModel: ListExpenseViewModel,
    onDelete: (expenseView: ExpenseView) -> Unit,
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit
) {
    val state = rememberLazyListState()
    LaunchedEffect(true) {
        viewModel.sendAction(ExpenseUiAction.UpdateExpenseMonthly)
    }

    val expenses = viewModel.uiState.collectAsState().value.expenses

    if (expenses.isEmpty()) {
        EmptyContent()
    } else {
        LazyColumn(
            state = state
        ) {
            items(
                items = expenses,
                key = { account ->
                    account.id
                }
            ) { account ->
                ListExpenseItem(
                    expense = account,
                    onDelete = onDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
                Divider(
                    modifier = Modifier.height(0.5.dp),
                    color = MaterialTheme.colorScheme.dividerColor
                )
            }
        }
    }
}

@Composable
private fun HomeCard(
    viewModel: ListExpenseViewModel,
    uiModel: ExpenseUiModel,
) {

    LaunchedEffect(true) {
        viewModel.sendAction(ExpenseUiAction.LoadData)
    }

    val cardState = CardState(
        valueTotal = uiModel.totalValue,
        paidOut = uiModel.paidOut,
        pending = uiModel.pending,
        percentage = uiModel.percentage
    )

    Box(modifier = Modifier.padding(horizontal = MyAccountsTheme.dimensions.padding16)) {
        StatisticsCard(cardState = cardState)
    }
}

private fun deleteExpense(viewModel: ListExpenseViewModel, expenseView: ExpenseView) {
    CoroutineScope(Dispatchers.Default).launch {
        viewModel.sendAction(ExpenseUiAction.DeleteExpense(expenseView))
        delay(300)
        getAllExpenseMonth(viewModel)
    }
}

private fun getAllExpenseMonth(viewModel: ListExpenseViewModel) {
    viewModel.sendAction(ExpenseUiAction.UpdateExpenseMonthly)
}