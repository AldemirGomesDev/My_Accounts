package br.com.aldemir.expense.presentation.expensedetail

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.component.DisplayAlertDialog
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextMyButton
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.component.TextTitleLarge
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.TASK_ITEM_ELEVATION
import br.com.aldemir.common.theme.dividerColor
import br.com.aldemir.common.theme.taskItemBackgroundColor
import br.com.aldemir.common.theme.taskItemTextColor
import br.com.aldemir.common.R
import br.com.aldemir.common.component.TopBar
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.expense.model.MonthlyPaymentView
import org.koin.androidx.compose.koinViewModel


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ExpenseDetailScreen(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    navigateToBackScreen: () -> Unit,
    viewModel: ExpenseDetailViewModel = koinViewModel(),
    expenseId: Int = -1,
    expenseName: String = emptyString()
) {
    val scaffoldState = rememberScaffoldState()

    val id by viewModel.id.observeAsState()

    LaunchedEffect(key1 = expenseId) {
        viewModel.getAllByIdExpense(expenseId)
    }

    BackHandler {
        navigateToBackScreen()
    }

    var monthlyPaymentToUpdate by remember {
        mutableStateOf(MonthlyPaymentView())
    }

    id?.let {
        if (it > 0) viewModel.getAllByIdExpense(expenseId)
    }

    val monthlyPayments by viewModel.monthlyPayment.collectAsState()

    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

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
                    color = MaterialTheme.colors.taskItemTextColor,
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
                    monthlyPayments = monthlyPayments,
                    onClickUpdate = { _, monthlyPayment ->
                        monthlyPaymentToUpdate = monthlyPayment.copy(situation = true)
                        viewModel.onOpenDialogClicked()
                    },
                    viewModel = viewModel
                )
                DisplayAlertDialog(
                    title = stringResource(id = R.string.dialog_confirm_alert_title),
                    message = stringResource(id = R.string.dialog_confirm_alert_message),
                    openDialog = showDialogState,
                    closeDialog = {
                        viewModel.onDialogDismiss()
                    },
                    onYesClicked = {
                        updateMonthlyPayment(monthlyPaymentToUpdate, viewModel)
                        viewModel.onDialogConfirm()
                    }
                )
            }
        },
    )
}

private fun updateMonthlyPayment(
    monthlyPayment: MonthlyPaymentView,
    viewModel: ExpenseDetailViewModel
) {
    viewModel.updateMonthlyPayment(monthlyPayment)
}

@Composable
private fun ExpenseDetailList(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    monthlyPayments: List<MonthlyPaymentView>,
    onClickUpdate: (Int, MonthlyPaymentView) -> Unit,
    viewModel: ExpenseDetailViewModel
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
                viewModel = viewModel,
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
    viewModel: ExpenseDetailViewModel,
    index: Int,
) {
    val context = LocalContext.current

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    val statusColor = viewModel.getStatusColor(monthlyPayment.situation, monthlyPayment.expired)
    val resourceId = viewModel.getStatusText(monthlyPayment.situation, monthlyPayment.expired)

    val buttonAlpha by animateFloatAsState(targetValue = if (monthlyPayment.situation) 0f else 1f,
        label = emptyString()
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MyAccountsTheme.colors.background,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MyAccountsTheme.colors.background)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (monthlyPayment.situation)
                                viewModel.showToast(
                                    context,
                                    context.getString(R.string.expense_no_update_message_toast)
                                )
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
                TextSubTitleItem(text = stringResource(id = R.string.account_label_value))
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
                    text = stringResource(id = resourceId),
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