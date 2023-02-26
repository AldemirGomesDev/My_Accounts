package br.com.aldemir.myaccounts.presentation.recipe.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.presentation.component.EmptyContent
import br.com.aldemir.myaccounts.presentation.theme.dividerColor
import br.com.aldemir.myaccounts.presentation.theme.taskItemBackgroundColor
import br.com.aldemir.myaccounts.util.DateUtils
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.StatisticsCard

@ExperimentalMaterialApi
@Composable
fun ListRecipeScreen(
    viewModel: ListRecipeViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
    navigateToDetailScreen: (recipeId: Int, recipeName: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val state = rememberLazyListState()

    BackHandler {
        navigateToHomeScreen()
    }

    LaunchedEffect(true) {
        viewModel.getAllRecipePerMonth(DateUtils.getMonth(), DateUtils.getYear())
        viewModel.getAllRecipeMonthly(DateUtils.getMonth(), DateUtils.getYear())
    }

    val recipes by viewModel.recipes.observeAsState()
    val cardState by viewModel.cardState.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colors.taskItemBackgroundColor),
            ) {
                StatisticsCard(cardState)
                recipes?.let {
                    if (it.isNotEmpty()) {
                        LazyColumn(
                            state = state
                        ) {
                            items(
                                items = it,
                                key = { recipeView ->
                                    recipeView.id
                                }
                            ) { recipeView ->
                                RecipeItem(
                                    recipeView = recipeView,
                                    viewModel = viewModel,
                                    onDelete = {},
                                    navigateToDetailScreen = { recipeId, nameRecipe ->
                                        navigateToDetailScreen(recipeId, nameRecipe)
                                    }
                                )
                                Divider(
                                    modifier = Modifier.height(0.5.dp),
                                    color = MaterialTheme.colors.dividerColor
                                )
                            }
                        }
                    } else EmptyContent(text = stringResource(id = R.string.recipe_text))
                }?:run {
                    EmptyContent()
                }
            }
        }
    )
}