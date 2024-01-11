package br.com.aldemir.recipe.presentation.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import br.com.aldemir.common.component.MyDropdownMenuItem
import br.com.aldemir.common.component.TextBodyTwoItem
import br.com.aldemir.common.component.TextDescriptionItem
import br.com.aldemir.common.component.TextSubTitleItem
import br.com.aldemir.common.component.TextTitleItem
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.LARGE_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.PRIORITY_INDICATOR_SIZE
import br.com.aldemir.common.theme.SMALL_PADDING
import br.com.aldemir.common.theme.TASK_ITEM_ELEVATION
import br.com.aldemir.common.theme.taskItemBackgroundColor
import br.com.aldemir.common.R
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.recipe.mapper.toDatabase
import br.com.aldemir.recipe.model.RecipeView

@ExperimentalMaterialApi
@Composable
fun RecipeItem(
    listItems: Array<DropdownItemState>,
    recipeView: RecipeView,
    viewModel: ListRecipeViewModel,
    onDelete: (recipeDTO: br.com.aldemir.data.database.model.RecipeDTO) -> Unit,
    navigateToDetailScreen: (recipeId: Int) -> Unit,
) {

    val statusColor = viewModel.getStatusColor(recipeView.status, recipeView.expired)
    val dueDate = String.format("%02d", recipeView.due_date)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToDetailScreen(recipeView.id)
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
                            TextTitleItem(text = recipeView.name, modifier = Modifier.weight(8f))
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
                            text = recipeView.description,
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
                                text = stringResource(viewModel.getStatusText(recipeView.status, recipeView.expired))
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
                    MyDropdownMenuItem(
                        onItemClicked = { type ->
                            when(type) {
                                DropdownItemType.DELETE -> { onDelete(recipeView.toDatabase()) }
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