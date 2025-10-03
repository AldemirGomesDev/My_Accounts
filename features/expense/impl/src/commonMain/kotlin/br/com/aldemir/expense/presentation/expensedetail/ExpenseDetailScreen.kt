package br.com.aldemir.expense.presentation.expensedetail

import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.component.DisplayAlertDialog
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextMyButton
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.showMessage
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.theme.taskItemTextColor
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.expense.model.MonthlyPaymentView
import br.com.aldemir.expense.presentation.expensedetail.action.ExpenseDetailAction
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_label_value
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.button_text_pay
import myaccounts.common.generated.resources.dialog_confirm_alert_message
import myaccounts.common.generated.resources.dialog_confirm_alert_title
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_no_update_message_toast
import myaccounts.common.generated.resources.expense_paid_out
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun ExpenseDetailScreen(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    navigateToBackScreen: () -> Unit,
    viewModel: ExpenseDetailViewModel = koinViewModel(),
    expenseId: Int = -1,
    expenseName: String = emptyString()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = expenseId) {
        viewModel.handleAction(ExpenseDetailAction.FetchExpenseDetail(expenseId))
    }

    BackHandler {
        navigateToBackScreen()
    }

    val uiModel by viewModel.uiModel.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MyAccountsTheme.colors.background)
            ) {
                TextTitleLarge(
                    text = expenseName,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = LARGE_PADDING, vertical = MEDIUM_PADDING)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Divider(
                    modifier = Modifier.height(MEDIUM_PADDING),
                    color = MyAccountsTheme.colors.background
                )
                ExpenseDetailList(
                    navigateToChangeScreen = navigateToChangeScreen,
                    monthlyPayments = uiModel.monthlyExpenses,
                    onClickUpdate = { _, monthlyPayment ->
                        viewModel.handleAction(ExpenseDetailAction.OnUpdateClicked(monthlyPayment.id))
                    },
                )
                DisplayAlertDialog(
                    title = stringResource(Res.string.dialog_confirm_alert_title),
                    message = stringResource(Res.string.dialog_confirm_alert_message),
                    openDialog = uiModel.showDialog,
                    closeDialog = {
                        viewModel.handleAction(ExpenseDetailAction.ShowDialog(false))
                    },
                    onYesClicked = {
                        viewModel.handleAction(
                            ExpenseDetailAction.UpdateExpenseSituation
                        )
                        viewModel.handleAction(ExpenseDetailAction.ShowDialog(false))
                    }
                )
            }
        },
    )
}

@Composable
private fun ExpenseDetailList(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    monthlyPayments: List<MonthlyPaymentView>,
    onClickUpdate: (Int, MonthlyPaymentView) -> Unit,
) {
    val state = rememberLazyListState()

    LazyColumn(
        state = state,
    ) {
        itemsIndexed(
            items = monthlyPayments,
        ) { index, monthlyPayment ->
            ExpenseDetailContent(
                navigateToChangeScreen = navigateToChangeScreen,
                monthlyPayment = monthlyPayment,
                onClickUpdate = onClickUpdate,
                index = index
            )
        }
    }
}

@Composable
private fun ExpenseDetailContent(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    monthlyPayment: MonthlyPaymentView,
    onClickUpdate: (Int, MonthlyPaymentView) -> Unit,
    index: Int,
) {
    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    val statusColor = getStatusColor(monthlyPayment.situation, monthlyPayment.expired)
    val resourceId = getStatusText(monthlyPayment.situation, monthlyPayment.expired)

    val buttonAlpha by animateFloatAsState(
        targetValue = if (monthlyPayment.situation) 0f else 1f,
        label = emptyString()
    )

    val message = stringResource(Res.string.expense_no_update_message_toast)

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MyAccountsTheme.colors.background,
        shape = RectangleShape,
        elevation = MyAccountsTheme.dimensions.sizing2,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MyAccountsTheme.colors.background)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            if (monthlyPayment.situation)
                                showMessage(message)
                            else navigateToChangeScreen(monthlyPayment.id)
                        },
                    )
                },
        ) {
            TextTitleItem(
                text = "${monthlyPayment.year} - ${monthlyPayment.month}",
            )
            Row(
                modifier = Modifier.background(MyAccountsTheme.colors.background),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextSubTitleItem(text = stringResource(Res.string.account_label_value))
                TextBodyTwoItem(
                    text = monthlyPayment.value.toCurrency(currencySymbol),
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
                TextBodyTwoItem(
                    text = " - ",
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
                TextBodyTwoItem(
                    modifier = Modifier.padding(start = SMALL_PADDING),
                    text = stringResource(resourceId),
                    color = statusColor,
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    enabled = !monthlyPayment.situation,
                    modifier = Modifier.alpha(buttonAlpha),
                    onClick = {
                        onClickUpdate(index, monthlyPayment)
                    }
                ) {
                    TextMyButton(text = stringResource(Res.string.button_text_pay))
                }
            }
            Divider(
                modifier = Modifier.height(0.5.dp),
                color = MaterialTheme.colorScheme.dividerColor
            )
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