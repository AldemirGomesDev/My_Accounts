package br.com.aldemir.expense.presentation.listexpense

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextDescriptionItem
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.formatTwoDigits
import br.com.aldemir.expense.model.ExpenseView
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_list_item_status
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_paid_out
import myaccounts.common.generated.resources.item_due_date
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@ExperimentalMaterialApi
@Composable
fun ListExpenseItem(
    expense: ExpenseView,
    onDelete: (expenseView: ExpenseView) -> Unit,
    navigateToTaskScreen: (taskId: Int, nameExpense: String) -> Unit
) {

    val statusColor = getStatusColor(expense.status, expense.expired)
    val dueDate = formatTwoDigits(expense.due_date)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MyAccountsTheme.colors.background,
        shape = RectangleShape,
        elevation = MyAccountsTheme.dimensions.sizing2,
        onClick = {
            navigateToTaskScreen(expense.id, expense.name)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING)
                .fillMaxWidth()
                .background(MyAccountsTheme.colors.background),
        ) {
            Row(
                modifier = Modifier.background(MyAccountsTheme.colors.background),
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
                                        .size(MyAccountsTheme.dimensions.sizing16)
                                ) { }
                            }
                        }
                        TextDescriptionItem(
                            text = expense.description,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Divider(
                            modifier = Modifier.height(MEDIUM_PADDING),
                            color = MyAccountsTheme.colors.background
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextSubTitleItem(text = stringResource(Res.string.item_due_date))
                            TextBodyTwoItem(
                                modifier = Modifier.padding(start = SMALL_PADDING),
                                text = dueDate
                            )
                            TextSubTitleItem(
                                modifier = Modifier.padding(start = LARGEST_PADDING),
                                text = stringResource(Res.string.account_list_item_status)
                            )
                            TextBodyTwoItem(
                                modifier = Modifier.padding(start = SMALL_PADDING),
                                color = statusColor,
                                text = stringResource(getStatusText(expense.status, expense.expired))
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.width(LARGEST_PADDING),
                    color = MyAccountsTheme.colors.background
                )
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                ) {
                    IconButton(onClick = { onDelete(expense) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            tint = MyAccountsTheme.colors.primary,
                            contentDescription = null
                        )
                    }
                }
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
