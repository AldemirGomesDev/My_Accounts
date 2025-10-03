package br.com.aldemir.expense.presentation.historic

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.component.EmptyContent
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.component.MyExposedDropdownMenu
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextDescriptionItem
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.formatTwoDigits
import br.com.aldemir.expense.mapper.toView
import br.com.aldemir.expense.model.ExpensePerMonthView
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.button_search
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_paid_out
import myaccounts.common.generated.resources.historic_month_text
import myaccounts.common.generated.resources.historic_year_text
import myaccounts.common.generated.resources.item_due_date
import myaccounts.common.generated.resources.months
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HistoricScreen(
    viewModel: HistoricViewModel = koinViewModel(),
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding)
                    .background(MyAccountsTheme.colors.background)
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

    val defaultOption by remember { mutableStateOf(DateUtils.getYearString()) }

    val myYears by viewModel.yearsList.collectAsState()

    val monthOptions = stringArrayResource(Res.array.months)
    var yearOptionSelected by remember { mutableStateOf(defaultOption) }
    var monthOptionSelected by remember { mutableStateOf(DateUtils.getMonthString()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                MyExposedDropdownMenu(
                    label = stringResource(Res.string.historic_month_text),
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
                    label = stringResource(Res.string.historic_year_text),
                    listItems = myYears.toList(),
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
                    .padding(bottom = 150.dp),
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
                    .height(52.dp),
                loading = viewModel.isLoading.value,
                enabled = enabled,
                text = stringResource(Res.string.button_search)
            )


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
    LaunchedEffect(Unit) {
        viewModel.getAllExpensePerMonth(DateUtils.getMonthString(), DateUtils.getYearString())
    }

    val expenses by viewModel.expensePerMonthDomain.collectAsState()

    if (expenses.isEmpty()) {
        EmptyContent(
            modifier = modifier
        )
    } else {
        Column(
            modifier = modifier.background(MyAccountsTheme.colors.background)
        ) {
            LazyColumn(state = state, modifier = Modifier.fillMaxWidth()) {
                items(
                    items = expenses,
                    key = { account ->
                        account.id_expense
                    }
                ) { account ->
                    HistoricItem(
                        expense = account.toView(),
                        navigateToHistoricScreen = navigateToHistoricScreen
                    )
                    Divider(
                        modifier = Modifier.height(0.5.dp),
                        color = MaterialTheme.colorScheme.dividerColor
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HistoricItem(
    expense: ExpensePerMonthView,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    val statusColor = getStatusColor(expense.situation, expense.expired)
    val dueDate = formatTwoDigits(expense.due_date)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MyAccountsTheme.colors.background,
        shape = RectangleShape,
        elevation = MyAccountsTheme.dimensions.sizing2,
        onClick = {
            navigateToHistoricScreen(expense.id_expense, expense.name)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MyAccountsTheme.colors.background),
        ) {
            Row {
                TextTitleItem(text = expense.name, modifier = Modifier.weight(8f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(MyAccountsTheme.dimensions.sizing16)
                    ) {
                        drawCircle(
                            color = statusColor
                        )
                    }
                }
            }
            TextDescriptionItem(
                text = expense.description,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                modifier = Modifier.height(SMALL_PADDING),
                color = MyAccountsTheme.colors.background
            )
            Row {
                TextSubTitleItem(text = stringResource(Res.string.item_due_date))
                TextBodyTwoItem(text = dueDate)
                TextBodyTwoItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(getStatusText(expense.situation, expense.expired)),
                    color = statusColor
                )
            }
        }
    }
}

private fun getStatusColor(status: Boolean, expired: Boolean): Color {
    return if (status) LowPriorityColor
    else if (expired) HighPriorityColor
    else MediumPriorityColor
}

private fun getStatusText(status: Boolean, expired: Boolean): StringResource {
    return if (status) Res.string.expense_paid_out
    else if (expired) Res.string.expense_expired
    else Res.string.account_pending
}
