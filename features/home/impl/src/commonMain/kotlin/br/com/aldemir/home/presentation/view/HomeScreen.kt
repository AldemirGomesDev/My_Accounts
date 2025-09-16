package br.com.aldemir.home.presentation.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.aldemir.myaccounts.common.R
import br.com.aldemir.common.component.LoadingAnimation
import br.com.aldemir.home.presentation.model.ButtonType
import br.com.aldemir.common.theme.*
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.home.presentation.state.HomeUiState
import me.bytebeats.views.charts.bar.BarChart
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.ILabelDrawer
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer
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

    val uiState by viewModel.uiState.collectAsState()

    val labelDrawer = viewModel.labelDrawer

    HomeContent(
        labelDrawer = labelDrawer,
        scaffoldState = scaffoldState,
        navigateToNextScreen = navigateToNextScreen,
        uiState = uiState
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    labelDrawer: SimpleLabelDrawer,
    scaffoldState: ScaffoldState,
    navigateToNextScreen: (ButtonType) -> Unit,
    uiState: HomeUiState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MyAccountsTheme.colors.background)
                    .padding(horizontal = MyAccountsTheme.dimensions.padding16),
            ) {
                HomeCard(homeCardData = uiState.uiModel.homeCardData)
                MyBarChart(
                    uiState = uiState,
                    barChartData = uiState.uiModel.barChartDataExpense,
                    labelDrawer = labelDrawer,
                    title = stringResource(id = R.string.expense_chart_title),
                    textEmpty = stringResource(id = R.string.expense_chart_empty),
                    buttonType = ButtonType.ButtonExpense,
                    navigateToNextScreen = navigateToNextScreen
                )
                MyBarChart(
                    uiState = uiState,
                    barChartData = uiState.uiModel.barChartDataRecipe,
                    labelDrawer = labelDrawer,
                    title = stringResource(id = R.string.recipe_chart_title),
                    textEmpty = stringResource(id = R.string.recipe_chart_empty),
                    buttonType = ButtonType.ButtonRecipe,
                    navigateToNextScreen = navigateToNextScreen
                )
            }
        }
    )
}

@Composable
private fun MyBarChart(
    uiState: HomeUiState,
    barChartData: BarChartData?,
    labelDrawer: ILabelDrawer,
    title: String,
    textEmpty: String,
    buttonType: ButtonType,
    navigateToNextScreen: (ButtonType) -> Unit,
) {

    Card(
        shape = Shapes.large,
        backgroundColor = MyAccountsTheme.colors.backgroundGreen,
        modifier = Modifier
            .padding(vertical = MyAccountsTheme.dimensions.padding16)
            .clickable {
                navigateToNextScreen(buttonType)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(top = MyAccountsTheme.dimensions.padding8)
                    .padding(bottom = MyAccountsTheme.dimensions.padding4),
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = MyAccountsFont
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = MyAccountsTheme.dimensions.padding8)
                    .background(MyAccountsTheme.colors.backgroundGreen),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                when(uiState) {
                    HomeUiState.Loading -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LoadingAnimation(
                                circleColor = White,
                                circleSize = MyAccountsTheme.dimensions.sizing16
                            )
                        }
                    }
                    is HomeUiState.ShowHomeCards -> {
                        barChartData?.let {
                            BarChart(
                                modifier = Modifier.padding(
                                    horizontal = MyAccountsTheme.dimensions.padding24,
                                    vertical = MyAccountsTheme.dimensions.padding8
                                ),
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
                        } ?: run {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(top = MyAccountsTheme.dimensions.padding8),
                                    painter = painterResource(id = R.drawable.ic_sad_face),
                                    contentDescription = stringResource(R.string.sad_face_icon),
                                    tint = MediumGray
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = MyAccountsTheme.dimensions.padding8)
                                        .padding(bottom = MyAccountsTheme.dimensions.padding4),
                                    text = textEmpty,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    color = MediumGray,
                                    fontFamily = MyAccountsFont
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeContentPreview() {
    MyAccountsTheme {
        HomeContent(
            labelDrawer = SimpleLabelDrawer(
                labelTextColor = White
            ),
            scaffoldState = rememberScaffoldState(),
            navigateToNextScreen = {},
            uiState = homeDataPreview
        )
    }
}