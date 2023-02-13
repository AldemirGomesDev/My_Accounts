package br.com.aldemir.myaccounts.presentation.expensedetail

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.presentation.component.*
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.swapList
import br.com.aldemir.myaccounts.util.toCurrency
import br.com.aldemir.myaccounts.util.updateList


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

    val monthlyPayments by viewModel.monthlyPayment.collectAsState()

    stateList.swapList(monthlyPayments)

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                ExpenseDetailList(
                    navigateToChangeScreen = navigateToChangeScreen,
                    monthlyPayments = stateList,
                    onClickUpdate = { index, monthlyPayment ->
                        monthlyPayment.situation = true
                        updateMonthlyPayment(monthlyPayment, viewModel)
                        stateList.updateList(index, monthlyPayment)
                    },
                    viewModel = viewModel
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
    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

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
                .background(MaterialTheme.colors.background)
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
                if (!monthlyPayment.situation) {
                    OutlinedButton(
                        onClick = { onClickUpdate(index, monthlyPayment) }
                    ) {
                        TextMyButton(text = stringResource(id = R.string.button_text_pay))
                    }
                }
            }
            DisplayAlertDialog(
                title = "Aviso",
                message = "Deseja confirmar o pagamento?",
                openDialog = showDialogState,
                closeDialog = {
                    viewModel.onDialogDismiss()
                },
                onYesClicked = {
                    onClickUpdate(index, monthlyPayment)
                    viewModel.onDialogConfirm()
                }
            )
        }
    }
}