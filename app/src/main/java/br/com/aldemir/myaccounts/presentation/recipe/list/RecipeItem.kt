package br.com.aldemir.myaccounts.presentation.recipe.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.domain.mapper.toDatabase
import br.com.aldemir.myaccounts.presentation.component.TextBodyTwoItem
import br.com.aldemir.myaccounts.presentation.component.TextDescriptionItem
import br.com.aldemir.myaccounts.presentation.component.TextSubTitleItem
import br.com.aldemir.myaccounts.presentation.component.TextTitleItem
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeView
import br.com.aldemir.myaccounts.presentation.theme.*

@ExperimentalMaterialApi
@Composable
fun RecipeItem(
    recipeView: RecipeView,
    viewModel: ListRecipeViewModel,
    onDelete: (recipe: Recipe) -> Unit,
    navigateToDetailScreen: (recipeId: Int, nameRecipe: String) -> Unit
) {

    val statusColor = viewModel.getStatusColor(recipeView.status, recipeView.expired)
    val dueDate = String.format("%02d", recipeView.due_date)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToDetailScreen(recipeView.id, recipeView.name)
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
                            TextSubTitleItem(text = stringResource(id = R.string.expense_due_date))
                            TextBodyTwoItem(
                                modifier = Modifier.padding(start = SMALL_PADDING),
                                text = dueDate
                            )
                            TextSubTitleItem(
                                modifier = Modifier.padding(start = LARGEST_PADDING),
                                text = stringResource(id = R.string.expense_status)
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
                    IconButton(onClick = { onDelete(recipeView.toDatabase()) }) {
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