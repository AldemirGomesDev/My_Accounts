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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
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
                Text(
                    modifier = Modifier.weight(8f),
                    text = expense.name,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
//                        drawCircle(
//                            color = toDoTask.priority.color
//                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = expense.description,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
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
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = expense.due_date.toString(),
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (expense.status) stringResource(id = R.string.expense_paid_out) else stringResource(
                        id = R.string.expense_pending
                    ),
                    color = if (expense.status) MaterialTheme.colors.paidTextColor else MediumPriorityColor,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
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
@Composable
@Preview
private fun TaskContentPreview() {
//    HandleListContent(
//        accounts = listOf(),
//        onSwipeToDelete = {},
//        navigateToTaskScreen =  {}
//    )
}