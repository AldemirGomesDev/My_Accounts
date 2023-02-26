package br.com.aldemir.myaccounts.presentation.home

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.model.Expense
import br.com.aldemir.myaccounts.presentation.component.DisplayAlertDialog
import br.com.aldemir.myaccounts.presentation.component.EmptyContent
import br.com.aldemir.myaccounts.presentation.component.StatisticsCard
import br.com.aldemir.myaccounts.presentation.home.component.TaskItem
import br.com.aldemir.myaccounts.presentation.shared.model.CardState
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit,
    navigateToAddScreen: () -> Unit,
    viewModel: HomeViewModel,
) {
    val scaffoldState = rememberScaffoldState()

    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    val activity = (LocalContext.current as? Activity)

    val context = LocalContext.current

    var expenseToSave by remember {
        mutableStateOf(Expense())
    }

    BackHandler { activity?.finish() }

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colors.taskItemBackgroundColor),
            ) {
                HomeCard(viewModel = viewModel)
                HomeScreenList(
                    navigateToTaskScreen = navigateToTaskScreen,
                    onDelete = { expense ->
                        expenseToSave = expense
                        viewModel.onOpenDialogClicked()
                    },
                    viewModel = viewModel
                )
                DisplayAlertDialog(
                    title = stringResource(id = R.string.dialog_delete_title),
                    message = stringResource(id = R.string.dialog_delete_message),
                    openDialog = showDialogState,
                    closeDialog = {
                        viewModel.onDialogDismiss()
                    },
                    onYesClicked = {
                        deleteExpense(viewModel, expenseToSave)
                        showToast(
                            context,
                            context.getString(
                                R.string.delete_expense_message_toast,
                                expenseToSave.id
                            )
                        )
                        viewModel.onDialogConfirm()
                    }
                )
            }
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToAddScreen)
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreenList(
    viewModel: HomeViewModel,
    onDelete: (expense: Expense) -> Unit,
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit
) {
    val state = rememberLazyListState()
    LaunchedEffect(true) {
        viewModel.getAllExpensePerMonth(DateUtils.getMonth(), DateUtils.getYear())
    }

    val expenses by viewModel.expenses.collectAsState()
    
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
                TaskItem(
                    expense = account,
                    viewModel = viewModel,
                    onDelete = onDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
                Divider(
                    modifier = Modifier.height(0.5.dp),
                    color = MaterialTheme.colors.dividerColor
                )
            }
        }
    }
}

@Composable
private fun HomeCard(
    viewModel: HomeViewModel,
) {

    LaunchedEffect(true) {
        viewModel.getAllExpensesMonth(DateUtils.getMonth(), DateUtils.getYear())
    }

    val valueTotal by viewModel.valueTotal.collectAsState()
    val paidOut by viewModel.paidOut.collectAsState()
    val pending by viewModel.pending.collectAsState()
    val percentage by viewModel.percentage.collectAsState()

    val cardState = CardState(
        valueTotal = valueTotal,
        paidOut = paidOut,
        pending = pending,
        percentage = percentage
    )

    StatisticsCard(cardState = cardState)
}

@Composable
fun LinearProgressIndicatorSample(
    value: Float,
    modifier: Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Box(modifier = modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = animatedProgress,
            color = LowPriorityColor,
            backgroundColor = MediumPriorityColor
        )
    }
}

@Composable
fun ListFab(
    onFabClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked()
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                id = R.string.add_account
            ),
            tint = Color.White
        )
    }
}

private fun deleteExpense(viewModel: HomeViewModel, expense: Expense) {
    CoroutineScope(Dispatchers.Default).launch {
        viewModel.delete(expense)
        delay(300)
        getAllExpenseMonth(viewModel)
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

private fun getAllExpenseMonth(viewModel: HomeViewModel) {
    viewModel.getAllExpensePerMonth(DateUtils.getMonth(), DateUtils.getYear())
}