package br.com.aldemir.myaccounts.presentation.historic

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.presentation.component.EmptyContent
import br.com.aldemir.myaccounts.presentation.component.LoadingButton
import br.com.aldemir.myaccounts.presentation.component.MyExposedDropdownMenu
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.DateUtils

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HistoricScreen(
    viewModel: HistoricViewModel = hiltViewModel(),
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(paddingValues = padding)
                    .fillMaxSize()
            ) {
                HistoricContent(viewModel, navigateToHistoricScreen)
            }
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun HistoricContent(
    viewModel: HistoricViewModel,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    viewModel.getAllMonthlyPayment()

    var enabled by remember { mutableStateOf(false) }

    enabled = !viewModel.isLoading.value

    val defaultOption by remember { mutableStateOf(DateUtils.getYear()) }

    val myYears by viewModel.yearsList.observeAsState()

    val monthOptions = stringArrayResource(id = R.array.months)
    var yearOptionSelected by remember { mutableStateOf(defaultOption) }
    var monthOptionSelected by remember { mutableStateOf(DateUtils.getMonth()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (constraintRow, constraintList, constraintButton) = createRefs()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(constraintRow) {
                        top.linkTo(parent.top, margin = 10.dp)
                    }
            ) {
                MyExposedDropdownMenu(
                    label = stringResource(id = R.string.month_text),
                    listItems = monthOptions.toList(),
                    selected = monthOptionSelected,
                    onItemSelected = { item ->
                        monthOptionSelected = item
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(end = 16.dp)
                )
                MyExposedDropdownMenu(
                    label = stringResource(id = R.string.year_text),
                    listItems = myYears?.toList() ?: listOf(),
                    selected = yearOptionSelected,
                    onItemSelected = { item ->
                        yearOptionSelected = item
                    },
                    modifier = Modifier.width(150.dp)
                )
            }
            HistoricScreenList(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 150.dp)
                    .constrainAs(constraintList) {
                        top.linkTo(constraintRow.top, margin = 70.dp)
                    },
                navigateToHistoricScreen = navigateToHistoricScreen
            )
            LoadingButton(
                onClick = {
                    viewModel.getAllExpensePerMonth(monthOptionSelected, yearOptionSelected)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .height(52.dp)
                    .constrainAs(constraintButton) {
                        bottom.linkTo(parent.bottom)
                    },
                loading = viewModel.isLoading.value,
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.search_button),
                    fontSize = FONT_SIZE_16,
                )
            }
        }

    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HistoricScreenList(
    viewModel: HistoricViewModel,
    modifier: Modifier = Modifier,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    val state = rememberLazyListState()
    LaunchedEffect(true) {
        viewModel.getAllExpensePerMonth(DateUtils.getMonth(), DateUtils.getYear())
    }

    val expenses by viewModel.expensePerMonth.observeAsState()

    if (expenses.isNullOrEmpty()) {
        EmptyContent(
            modifier = modifier
        )
    } else {
        Column(
            modifier = modifier
        ) {
            LazyColumn(state = state, modifier = Modifier.fillMaxWidth()) {
                items(
                    items = expenses!!,
                    key = { account ->
                        account.id_expense
                    }
                ) { account ->
                    HistoricItem(
                        expense = account,
                        navigateToHistoricScreen = navigateToHistoricScreen
                    )
                    Divider(
                        color = LightGray
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HistoricItem(
    expense: ExpensePerMonth,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToHistoricScreen(expense.id_expense, expense.name)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = expense.name,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = Typography.body1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = if (expense.situation) LowPriorityColor else MediumPriorityColor
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = expense.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = Typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Divider(
                modifier = Modifier.height(SMALL_PADDING),
                color = MaterialTheme.colors.background
            )
            Row {
                Text(
                    text = stringResource(id = R.string.expense_due_date),
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = Typography.subtitle2,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = expense.due_date.toString(),
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = Typography.body2,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (expense.situation) stringResource(id = R.string.expense_paid_out) else stringResource(
                        id = R.string.expense_pending
                    ),
                    color = if (expense.situation) MaterialTheme.colors.paidTextColor else MediumPriorityColor,
                    style = Typography.body2,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
@Preview(showBackground = true)
private fun HistoricScreenPreview() {
}

fun TextFieldValue.isBlank() = this.text.isBlank()
