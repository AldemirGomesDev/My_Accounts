package br.com.aldemir.myaccounts.presentation.expensedetail

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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.presentation.component.*
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.swapList
import br.com.aldemir.myaccounts.util.toCurrency


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ExpenseDetailScreen(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    navigateToBackScreen: () -> Unit,
    viewModel: ExpenseDetailViewModel = hiltViewModel(),
    expenseId: Int = -1,
    expenseName: String = emptyString()
) {
    val scaffoldState = rememberScaffoldState()

    viewModel.getAllByIdExpense(expenseId)

    val stateList = remember { mutableStateListOf<MonthlyPayment>() }

    var monthlyPaymentToUpdate by remember {
        mutableStateOf(MonthlyPayment())
    }

    val id by viewModel.id.observeAsState()

    id?.let {
        viewModel.getAllByIdExpense(it)
    }

    val monthlyPayments by viewModel.monthlyPayment.collectAsState()

    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    stateList.swapList(monthlyPayments)

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                TextTitleLarge(
                    text = expenseName,
                    color = Purple700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = LARGE_PADDING, vertical = MEDIUM_PADDING)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
                Divider(
                    modifier = Modifier.height(MEDIUM_PADDING),
                    color = MaterialTheme.colors.background
                )
                ExpenseDetailList(
                    navigateToChangeScreen = navigateToChangeScreen,
                    monthlyPayments = monthlyPayments,
                    onClickUpdate = { index, monthlyPayment ->
                        monthlyPaymentToUpdate = monthlyPayment.copy(situation = true)
                        viewModel.onOpenDialogClicked()
                    },
                    viewModel = viewModel
                )
                DisplayAlertDialog(
                    title = "Aviso",
                    message = "Deseja confirmar o pagamento?",
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
    monthlyPayment: MonthlyPayment,
    viewModel: ExpenseDetailViewModel
) {
    viewModel.updateMonthlyPayment(monthlyPayment)
}

@Composable
private fun ExpenseDetailList(
    navigateToChangeScreen: (idMonthlyPayment: Int) -> Unit,
    monthlyPayments: List<MonthlyPayment>,
    onClickUpdate: (Int, MonthlyPayment) -> Unit,
    viewModel: ExpenseDetailViewModel
) {
    val state = rememberLazyListState()

    LazyColumn(state = state) {
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
    monthlyPayment: MonthlyPayment,
    onClickUpdate: (Int, MonthlyPayment) -> Unit,
    viewModel: ExpenseDetailViewModel,
    index: Int,
) {

    val buttonAlpha by animateFloatAsState(targetValue = if (monthlyPayment.situation) 0f else 1f)

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MaterialTheme.colors.taskItemBackgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            navigateToChangeScreen(monthlyPayment.id)
                        },
                    )
                },
        ) {
            TextTitleItem(
                text = "${monthlyPayment.year} - ${monthlyPayment.month}",
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextSubTitleItem(text = stringResource(id = R.string.label_value))
                TextBodyTwoItem(
                    text = monthlyPayment.value.toCurrency(),
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
                TextBodyTwoItem(
                    text = " - ",
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
                TextBodyTwoItem(
                    modifier = Modifier.padding(start = SMALL_PADDING),
                    text = viewModel.checkPaidOut(monthlyPayment.situation),
                    color = if (monthlyPayment.situation) LowPriorityColor else MediumPriorityColor,
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
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