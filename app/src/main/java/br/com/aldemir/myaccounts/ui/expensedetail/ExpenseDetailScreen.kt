package br.com.aldemir.myaccounts.ui.expensedetail

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.ui.theme.*
import br.com.aldemir.myaccounts.util.Const.TAG
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
        topBar = {
            TopAppBar(
                title = {
                    Text(text = expenseName, color = White)
                },
                backgroundColor = MaterialTheme.colors.topAppBarBackGroundColor
            )
        },
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colors.taskItemBackgroundColor,
                shape = RectangleShape,
                elevation = TASK_ITEM_ELEVATION,
            ) {
                Column(
                    modifier = Modifier
                        .padding(all = LARGE_PADDING)
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
                    Text(
                        text = monthlyPayment.month,
                        color = MaterialTheme.colors.taskItemTextColor,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = stringResource(id = R.string.label_value),
                            color = MaterialTheme.colors.taskItemTextColor,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.padding(start = SMALL_PADDING),
                            text = monthlyPayment.value.toCurrency(),
                            color = MaterialTheme.colors.taskItemTextColor,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.padding(start = SMALL_PADDING),
                            text = " - ",
                            color = MaterialTheme.colors.taskItemTextColor,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.padding(start = SMALL_PADDING),
                            text = viewModel.checkPaidOut(monthlyPayment.situation),
                            color = if (monthlyPayment.situation) LowPriorityColor else MediumPriorityColor,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (!monthlyPayment.situation) {
                            OutlinedButton(
                                onClick = { onClickUpdate(index, monthlyPayment) }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.button_text_pay),
                                    color = MaterialTheme.colors.taskItemTextColor,
                                    style = MaterialTheme.typography.button,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}