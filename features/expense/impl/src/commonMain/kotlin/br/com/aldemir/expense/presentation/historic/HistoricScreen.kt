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
import br.com.aldemir.common.component.LoadingAnimation
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
import br.com.aldemir.common.util.DateUtils.getMonthByLanguage
import br.com.aldemir.common.util.DateUtils.getMonthNameFromPortuguese
import br.com.aldemir.common.util.formatTwoDigits
import br.com.aldemir.expense.presentation.historic.action.HistoricExpenseAction
import br.com.aldemir.expense.presentation.historic.model.ExpensePerMonthUiModel
import br.com.aldemir.expense.presentation.historic.model.HistoricExpenseUiModel
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
    LaunchedEffect(Unit) {
        viewModel.handleEvent(HistoricExpenseAction.FetchData)
        viewModel.handleEvent(HistoricExpenseAction.UpdateMonthSelected(DateUtils.getMonthString()))
    }
    val scaffoldState = rememberScaffoldState()

    val uiModel by viewModel.uiModel.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            HistoricContent(
                modifier = Modifier.padding(padding),
                uiModel = uiModel,
                navigateToHistoricScreen = navigateToHistoricScreen,
                onSearchClicked = { month, year ->
                    viewModel.handleEvent(
                        HistoricExpenseAction.OnSearchClicked(month, year)
                    )
                },
                onItemSelected = {
                    viewModel.handleEvent(HistoricExpenseAction.UpdateMonthSelected(it))
                }
            )
        }
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun HistoricContent(
    modifier: Modifier = Modifier,
    uiModel: HistoricExpenseUiModel,
    onItemSelected: (item: String) -> Unit,
    onSearchClicked: (month: String, year: String) -> Unit,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {

    var enabled by remember { mutableStateOf(true) }

    enabled = !uiModel.isLoading

    val defaultOption by remember { mutableStateOf(DateUtils.getYearString()) }

    val monthOptions = stringArrayResource(Res.array.months)
    var yearOptionSelected by remember { mutableStateOf(defaultOption) }
    var monthOptionSelected by remember { mutableStateOf(DateUtils.getMonthString()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyAccountsTheme.colors.background)
            .padding(top = MyAccountsTheme.dimensions.padding16)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            MyExposedDropdownMenu(
                label = stringResource(Res.string.historic_month_text),
                listItems = monthOptions.toList(),
                selected = getMonthByLanguage(monthOptionSelected),
                onItemSelected = { item ->
                    onItemSelected(getMonthNameFromPortuguese(item))
                    monthOptionSelected = item
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MyAccountsTheme.dimensions.padding4)
            )
            MyExposedDropdownMenu(
                label = stringResource(Res.string.historic_year_text),
                listItems = uiModel.yearsList,
                selected = yearOptionSelected,
                onItemSelected = { item ->
                    yearOptionSelected = item
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MyAccountsTheme.dimensions.padding4)
            )
        }
        HistoricScreenList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 150.dp),
            navigateToHistoricScreen = navigateToHistoricScreen,
            expenses = uiModel.expensePerMonthUiModelList,
            isLoading = uiModel.isLoading
        )

        Spacer(modifier = Modifier.weight(1f))

        LoadingButton(
            onClick = {
                onSearchClicked.invoke(monthOptionSelected, yearOptionSelected)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
                .height(52.dp),
            loading = uiModel.isLoading,
            enabled = enabled,
            text = stringResource(Res.string.button_search)
        )


    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HistoricScreenList(
    expenses: List<ExpensePerMonthUiModel>,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    val state = rememberLazyListState()

    if (isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation(
                circleSize = MyAccountsTheme.dimensions.sizing24,
                circleColor = MyAccountsTheme.colors.primary
            )
        }
    }
    else if (expenses.isEmpty()) {
        EmptyContent(
            modifier = modifier
        )
    }
    else {
        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MyAccountsTheme.dimensions.padding16)
        ) {
            items(
                items = expenses,
                key = { account ->
                    account.idExpense
                }
            ) { expense ->
                HistoricItem(
                    expense = expense,
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

@ExperimentalMaterialApi
@Composable
fun HistoricItem(
    expense: ExpensePerMonthUiModel,
    navigateToHistoricScreen: (taskId: Int, nameExpense: String) -> Unit,
) {
    val statusColor = getStatusColor(expense.situation, expense.expired)
    val dueDate = formatTwoDigits(expense.dueDate)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MyAccountsTheme.colors.background,
        shape = RectangleShape,
        elevation = MyAccountsTheme.dimensions.sizing2,
        onClick = {
            navigateToHistoricScreen(expense.idExpense, expense.name)
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
