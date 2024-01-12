package br.com.aldemir.expense.presentation.listexpense

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.PRIORITY_INDICATOR_SIZE
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.TASK_ITEM_ELEVATION
import br.com.aldemir.common.theme.taskItemBackgroundColor
import br.com.aldemir.common.theme.taskItemTextColor
import br.com.aldemir.common.R
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextDescriptionItem
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.expense.model.ExpenseView

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ListExpenseItem(
    expense: ExpenseView,
    viewModel: ListExpenseViewModel,
    onDelete: (expenseView: ExpenseView) -> Unit,
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit
) {

    val statusColor = viewModel.getStatusColor(expense.status, expense.expired)
    val dueDate = String.format("%02d", expense.due_date)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(expense.id, expense.name)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
        ) {
            Row(
                modifier = Modifier.background(MaterialTheme.colors.background),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .weight(8f)
                    .fillMaxSize()
                ) {
                    Column {
                        Row {
                            TextTitleItem(text = expense.name, modifier = Modifier.weight(8f))
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                Canvas(
                                    modifier = Modifier
                                        .size(PRIORITY_INDICATOR_SIZE)
                                ) {
//                                    drawCircle(
//                                        color = statusColor
//                                    )
                                }
                            }
                        }
                        TextDescriptionItem(
                            text = expense.description,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Divider(
                            modifier = Modifier.height(MEDIUM_PADDING),
                            color = MaterialTheme.colors.background
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextSubTitleItem(text = stringResource(id = R.string.item_due_date))
                            TextBodyTwoItem(
                                modifier = Modifier.padding(start = SMALL_PADDING),
                                text = dueDate
                            )
                            TextSubTitleItem(
                                modifier = Modifier.padding(start = LARGEST_PADDING),
                                text = stringResource(id = R.string.account_list_item_status)
                            )
                            TextBodyTwoItem(
                                modifier = Modifier.padding(start = SMALL_PADDING),
                                color = statusColor,
                                text = stringResource(viewModel.getStatusText(expense.status, expense.expired))
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.width(LARGEST_PADDING),
                    color = MaterialTheme.colors.background
                )
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                ) {
                    IconButton(onClick = { onDelete(expense) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            tint = MaterialTheme.colors.taskItemTextColor,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@Preview
private fun TaskContentPreview() {
//    HandleListContent(
//        accounts = listOf(),
//        onSwipeToDelete = {},
//        navigateToTaskScreen =  {}
//    )
}