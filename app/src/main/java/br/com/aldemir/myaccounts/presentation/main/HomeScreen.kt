package br.com.aldemir.myaccounts.presentation.main

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.presentation.main.component.DisplayAlertDialog
import br.com.aldemir.myaccounts.presentation.main.component.RedBackground
import br.com.aldemir.myaccounts.presentation.main.component.TaskItem
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.*
import br.com.aldemir.myaccounts.util.Const.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit,
    navigateToAddScreen: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                HomeCard(viewModel = viewModel)
                HomeScreenList(
                    navigateToTaskScreen = navigateToTaskScreen,
                    viewModel = viewModel
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
    viewModel: MainViewModel,
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit
) {
    val state = rememberLazyListState()
    LaunchedEffect(true) {
        viewModel.getAllExpensePerMonth(DateUtils.getMonth(), DateUtils.getYear())
    }

    val expenses by viewModel.expenses.collectAsState()

    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    viewModel.onOpenDialogClicked()

    LazyColumn(state = state) {
        items(
            items = expenses,
            key = { account ->
                account.id
            }
        ) { account ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            val progress = (dismissState.progress.fraction * 100.0).roundToInt() / 100.0

//            Log.w(TAG, "HomeScreenList progress: ${progress.compareTo(1.0)}" )
            if (progress.compareTo(0.9) == -1) {
//                Log.w(TAG, "HomeScreenList: ${dismissState.currentValue}" )
                viewModel.onOpenDialogClicked()
            }
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {

                DisplayAlertDialog(
                    title = "Aviso",
                    message = "Deseja realmente excluir?",
                    openDialog = showDialogState,
                    closeDialog = {
                        viewModel.onDialogDismiss()
                        getAllExpenseMonth(viewModel)
                    },
                    onYesClicked = {
                        viewModel.onDialogConfirm()
                        deleteExpense(viewModel, account)
                    }
                )
            }

            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default)
                    0f
                else
                    -45f
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkHorizontally(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        TaskItem(
                            expense = account,
                            navigateToTaskScreen = navigateToTaskScreen
                        )
                    }
                )

                Divider(
                    color = colorResource(id = R.color.grey)
                )
            }
        }

    }
}

@Composable
private fun HomeCard(
    viewModel: MainViewModel,
) {

    LaunchedEffect(true) {
        viewModel.getAllExpensesMonth(DateUtils.getMonth(), DateUtils.getYear())
    }
    val stateList = remember { mutableStateListOf<MonthlyPayment>() }

    val expensesMonths by viewModel.monthExpenses.collectAsState()

    stateList.swapList(expensesMonths)

    var valueTotal = 0.0
    var paidOut = 0.0
    var pending = 0.0
    for (item in stateList) {
        valueTotal += item.value
        if (item.situation) {
            paidOut += item.value
        } else {
            pending += item.value
        }
    }
    val percentage = ((paidOut / valueTotal) * 100).toFloat()

    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.fabBackgroundColor,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .padding(16.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (totalLabel, totalValue, paidOutLabel, paidOutValue,
                    payableLabel, payableValue, progressValue, iconPaidOut, iconPayable) = createRefs()
                Text(
                    modifier = Modifier
                        .constrainAs(totalLabel) {
                            top.linkTo(parent.top)
                        }
                        .padding(bottom = 20.dp),
                    text = stringResource(id = R.string.total_month),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .constrainAs(totalValue) {
                            end.linkTo(parent.end)
                        }
                        .padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    text = valueTotal.toCurrency(),
                    color = Color.White
                )
                Image(
                    modifier = Modifier.constrainAs(iconPaidOut) {
                        top.linkTo(paidOutLabel.top)
                        bottom.linkTo(paidOutValue.bottom)
                        start.linkTo(parent.start)
                    },
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier
                        .constrainAs(paidOutLabel) {
                            top.linkTo(totalLabel.bottom)
                            start.linkTo(iconPaidOut.end)
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    text = "Pago",
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .constrainAs(paidOutValue) {
                            top.linkTo(paidOutLabel.bottom)
                            start.linkTo(iconPaidOut.end)
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    text = paidOut.toCurrency(),
                    color = Color.White
                )
                Image(
                    modifier = Modifier
                        .constrainAs(iconPayable) {
                            top.linkTo(payableLabel.top)
                            bottom.linkTo(payableValue.bottom)
                            start.linkTo(paidOutValue.end)
                        }
                        .padding(start = 24.dp),
                    painter = painterResource(id = R.drawable.ic_report_problem),
                    contentDescription = emptyString()
                )
                Text(
                    modifier = Modifier
                        .constrainAs(payableLabel) {
                            top.linkTo(totalLabel.bottom)
                            start.linkTo(iconPayable.end)
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    text = "À pagar",
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .constrainAs(payableValue) {
                            top.linkTo(paidOutLabel.bottom)
                            start.linkTo(iconPayable.end)
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    text = pending.toCurrency(),
                    color = Color.White
                )
                LinearProgressIndicatorSample(
                    value = if (!percentage.isNaN()) {
                        percentage.toDecimal()
                    } else {
                        emptyFloat()
                    },
                    modifier = Modifier
                        .constrainAs(progressValue) {
                            top.linkTo(payableValue.bottom)
                        }
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(top = 12.dp)
                        .clip(shape = Shapes.large)
                )
            }
        }

    }
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

private fun deleteExpense(viewModel: MainViewModel, expense: Expense) {
    CoroutineScope(Dispatchers.Default).launch {
        delay(300)
        viewModel.delete(expense)
    }
}

private fun getAllExpenseMonth(viewModel: MainViewModel) {
    viewModel.getAllExpensePerMonth(DateUtils.getMonth(), DateUtils.getYear())
}