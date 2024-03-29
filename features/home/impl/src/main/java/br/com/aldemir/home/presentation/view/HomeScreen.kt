package br.com.aldemir.home.presentation.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import br.com.aldemir.common.R
import br.com.aldemir.home.presentation.model.ButtonType
import br.com.aldemir.home.presentation.model.HomeButtonType
import br.com.aldemir.common.theme.*
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.ILabelDrawer
import me.bytebeats.views.charts.bar.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.bar.render.yaxis.SimpleYAxisDrawer
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
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
                    title = stringResource(id = R.string.recipe_chart_title),
                    buttonType = ButtonType.ButtonRecipe,
                    navigateToNextScreen = navigateToNextScreen
                )
                MyBarChart(
                    barChartData = barChartDataExpense,
                    labelDrawer = labelDrawer,
                    title = stringResource(id = R.string.expense_chart_title),
                    buttonType = ButtonType.ButtonExpense,
                    navigateToNextScreen = navigateToNextScreen
                )
            }
        }
    )
}

@Composable
private fun MyBarChart(
    barChartData: BarChartData,
    labelDrawer: ILabelDrawer,
    title: String,
    buttonType: ButtonType,
    navigateToNextScreen: (ButtonType) -> Unit,
) {

    Card(
        shape = Shapes.large,
        backgroundColor = GreenDark,
        modifier = Modifier.padding(vertical = 16.dp)
            .clickable {
                navigateToNextScreen(buttonType)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(top = MEDIUM_PADDING)
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
                            Text(text = button.name, color = White)
                        }
                    }
                }
            }
        }
    )
}