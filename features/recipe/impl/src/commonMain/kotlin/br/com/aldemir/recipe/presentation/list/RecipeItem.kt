package br.com.aldemir.recipe.presentation.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.intl.Locale
import br.com.aldemir.common.component.MyDropdownMenuItem
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextDescriptionItem
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.taskItemBackgroundColor
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.util.formatTwoDigits
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.recipe.model.RecipeView
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_list_item_status
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.home_expense_expired
import myaccounts.common.generated.resources.home_expense_paid_out
import myaccounts.common.generated.resources.home_recipe_checked
import myaccounts.common.generated.resources.home_recipe_pending
import myaccounts.common.generated.resources.item_due_date
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@ExperimentalMaterialApi
@Composable
fun RecipeItem(
    listItems: List<DropdownItemState>,
    recipeView: RecipeView,
    onDelete: (recipeView: RecipeView) -> Unit,
    navigateToDetailScreen: (recipeId: Int) -> Unit,
) {

    val statusColor = getStatusColor(recipeView.status, recipeView.expired)
    val dueDate = formatTwoDigits(recipeView.due_date)

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = MyAccountsTheme.dimensions.sizing2,
        onClick = {
            navigateToDetailScreen(recipeView.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyAccountsTheme.colors.background)
                .padding(horizontal = LARGE_PADDING, vertical = SMALL_PADDING),
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextTitleItem(text = recipeView.name)
                            TextSubTitleItem(text = recipeView.value.toCurrency(currencySymbol))
//                            Box(modifier = Modifier
//                                .fillMaxWidth(),
//                                contentAlignment = Alignment.TopEnd
//                            ) {
//                                Canvas(
//                                    modifier = Modifier
//                                        .size(MyAccountsTheme.dimensions.sizing16)
//                                ) {
//                                    drawCircle(
//                                        color = statusColor
//                                    )
//                                }
//                            }
                        }
                        TextDescriptionItem(
                            text = recipeView.description,
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
                                text = stringResource(getStatusText(recipeView.status, recipeView.expired))
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
                    MyDropdownMenuItem(
                        onItemClicked = { type ->
                            when(type) {
                                DropdownItemType.DELETE -> { onDelete(recipeView) }
                                DropdownItemType.UPDATE -> { navigateToDetailScreen(recipeView.id) }
                                else -> {}
                            }
                        },
                        listItems = listItems
                    )
                }
            }
        }
    }
}

fun getStatusColor(status: Boolean, expired: Boolean): Color {
    return if (status) LowPriorityColor
    else if (expired) HighPriorityColor
    else MediumPriorityColor
}

fun getStatusText(status: Boolean, expired: Boolean): StringResource {
    return if (status) Res.string.home_recipe_checked
    else if (expired) Res.string.home_recipe_pending
    else Res.string.account_pending
}