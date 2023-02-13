package br.com.aldemir.myaccounts.presentation.main.component

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
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.presentation.component.TextBodyTwoItem
import br.com.aldemir.myaccounts.presentation.component.TextDescriptionItem
import br.com.aldemir.myaccounts.presentation.component.TextSubTitleItem
import br.com.aldemir.myaccounts.presentation.component.TextTitleItem
import br.com.aldemir.myaccounts.presentation.theme.*

@Composable
fun RedBackground(degrees: Float) {
    Box(modifier = Modifier
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
fun TaskItem(
    expense: Expense,
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit
) {
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
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
        ) {
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
                        drawCircle(
                            color = if (expense.status) LowPriorityColor else MediumPriorityColor
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
                color = MaterialTheme.colors.background
            )
            Row {
                TextSubTitleItem(text = stringResource(id = R.string.expense_due_date))
                TextBodyTwoItem(text = expense.due_date.toString())
                TextBodyTwoItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (expense.status) stringResource(id = R.string.expense_paid_out) else stringResource(
                        id = R.string.expense_pending
                    ),
                    color = if (expense.status) MaterialTheme.colors.paidTextColor else MediumPriorityColor,
                )
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