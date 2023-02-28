package br.com.aldemir.myaccounts.presentation.home

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.HomeCard
import br.com.aldemir.myaccounts.presentation.home.state.ButtonType
import br.com.aldemir.myaccounts.presentation.home.state.HomeButtonType
import br.com.aldemir.myaccounts.presentation.theme.*
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.ILabelDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNextScreen: (ButtonType) -> Unit,
){
    LaunchedEffect(true) {
        viewModel.getAllRecipeAndExpense()
        viewModel.getAllExpenseSixMonthsPrevious()
        viewModel.getAllRecipesSixMonthsPrevious()
    }
    val scaffoldState = rememberScaffoldState()

    val activity = (LocalContext.current as? Activity)

    BackHandler { activity?.finish() }

    val homeCardData by viewModel.homeCardData.collectAsState()
    val barChartDataExpense by viewModel.barChartDataExpense.collectAsState()
    val barChartDataRecipe by viewModel.barChartDataRecipe.collectAsState()
    val labelDrawer = viewModel.labelDrawer

    val buttons = listOf(
        HomeButtonType(name = stringResource(id = R.string.button_recipe), type = ButtonType.ButtonRecipe),
        HomeButtonType(name = stringResource(id = R.string.button_expense), type = ButtonType.ButtonExpense),
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
                HomeCard(homeCardData = homeCardData)
                MyBarChart(
                    barChartData = barChartDataRecipe,
                    labelDrawer = labelDrawer,
                    title = stringResource(id = R.string.chart_recipe)
                )
                MyBarChart(
                    barChartData = barChartDataExpense,
                    labelDrawer = labelDrawer,
                    title = stringResource(id = R.string.chart_expense)
                )
                ButtonsHomeGrid(
                    navigateToNextScreen = navigateToNextScreen,
                    buttons = buttons
                )
            }
        }
    )
}

@Composable
private fun MyBarChart(
    barChartData: BarChartData,
    labelDrawer: ILabelDrawer,
    title: String
) {

    Card(
        shape = Shapes.large,
        backgroundColor = GreenDark,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = MEDIUM_PADDING)
                    .padding(bottom = SMALL_PADDING),
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = MEDIUM_PADDING)
                    .background(GreenDark)
            ){
                BarChart(
                    modifier = Modifier.padding(horizontal = LARGEST_PADDING, vertical = MEDIUM_PADDING),
                    barChartData = barChartData,
                    labelDrawer = labelDrawer,
                    xAxisDrawer = SimpleXAxisDrawer(
                        axisLineColor = White
                    ),
                    yAxisDrawer = SimpleYAxisDrawer(
                        axisLineColor = White,
                        labelTextColor = White,
                    ),
                )
            }
        }
    }
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