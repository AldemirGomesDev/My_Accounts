package br.com.aldemir.myaccounts.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.aldemir.myaccounts.presentation.component.StatisticsCard
import br.com.aldemir.myaccounts.presentation.home.state.ButtonType
import br.com.aldemir.myaccounts.presentation.home.state.HomeButtonType
import br.com.aldemir.myaccounts.presentation.shared.model.CardState
import br.com.aldemir.myaccounts.presentation.shared.model.CardType
import br.com.aldemir.myaccounts.presentation.theme.*

@Composable
fun HomeScreen(
    navigateToNextScreen: (ButtonType) -> Unit,
){
    val scaffoldState = rememberScaffoldState()

    val activity = (LocalContext.current as? Activity)

    BackHandler { activity?.finish() }

    val cardState = CardState(
        valueTotal = 2300.0,
        paidOut = 1300.0,
        pending = 1000.0,
        percentage = 60F,
        cardType = CardType.HOME
    )

    val buttons = listOf(
        HomeButtonType(name = "Receitas", type = ButtonType.ButtonRecipe),
        HomeButtonType(name = "Despesas", type = ButtonType.ButtonExpense),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = LARGE_PADDING_16)
                    .background(MaterialTheme.colors.taskItemBackgroundColor),
            ) {
                StatisticsCard(cardState = cardState)
                ButtonsHomeGrid(
                    navigateToNextScreen = navigateToNextScreen,
                    buttons = buttons
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonsHomeGrid(
    navigateToNextScreen: (ButtonType) -> Unit,
    buttons: List<HomeButtonType>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 168.dp),
        content = {
            items(buttons) { button ->
                Card(
                    shape = Shapes.large,
                    backgroundColor = GreenDark,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = GreenDark,
                        shape = RectangleShape,
                        elevation = TASK_ITEM_ELEVATION,
                        onClick = {
                            navigateToNextScreen(button.type)
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(text = button.name)
                        }
                    }
                }
            }
        }
    )
}