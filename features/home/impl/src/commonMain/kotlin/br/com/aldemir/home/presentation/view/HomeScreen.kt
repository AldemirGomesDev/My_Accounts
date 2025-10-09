package br.com.aldemir.home.presentation.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.aldemir.common.component.LoadingAnimation
import br.com.aldemir.home.presentation.model.ButtonType
import br.com.aldemir.common.theme.*
import br.com.aldemir.home.presentation.action.HomeAction
import br.com.aldemir.home.presentation.model.BarChart
import br.com.aldemir.home.presentation.state.HomeUiState
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.expense_chart_empty
import myaccounts.common.generated.resources.expense_chart_title
import myaccounts.common.generated.resources.recipe_chart_empty
import myaccounts.common.generated.resources.recipe_chart_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navigateToNextScreen: (ButtonType) -> Unit,
    onFinish: () -> Unit,
) {
    LaunchedEffect(true) {
        viewModel.handleAction(HomeAction.FetchData)
    }
    val scaffoldState = rememberScaffoldState()

    BackHandler { onFinish.invoke() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        scaffoldState = scaffoldState,
        navigateToNextScreen = navigateToNextScreen,
        uiState = uiState
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
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
                    barCharts = uiState.uiModel.barChartDataExpenses,
                    title = stringResource(Res.string.expense_chart_title),
                    textEmpty = stringResource(Res.string.expense_chart_empty),
                    buttonType = ButtonType.ButtonExpense,
                    navigateToNextScreen = navigateToNextScreen
                )
                MyBarChart(
                    uiState = uiState,
                    barCharts = uiState.uiModel.barChartDataRecipes,
                    title = stringResource(Res.string.recipe_chart_title),
                    textEmpty = stringResource(Res.string.recipe_chart_empty),
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
    barCharts: List<BarChart>,
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
                    .height(200.dp)
                    .padding(vertical = MyAccountsTheme.dimensions.padding8)
                    .background(MyAccountsTheme.colors.backgroundGreen),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (uiState) {
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
                        if (barCharts.isNotEmpty()) {
                            val data = barCharts.map {
                                Bars(
                                    label = it.label,
                                    values = listOf(
                                        Bars.Data(
                                            value = it.value,
                                            color = SolidColor(it.color),
                                        ),
                                    )
                                )
                            }
                            ColumnChart(
                                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                                data = remember { data },
                                barProperties = BarProperties(
                                    cornerRadius = Bars.Data.Radius.Rectangle(topRight = 4.dp, topLeft = 4.dp),
                                    spacing = 3.dp,
                                    thickness = 20.dp
                                ),
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                labelProperties = LabelProperties(
                                    textStyle = TextStyle.Default.copy(color = White),
                                    enabled = true
                                ),
                                indicatorProperties = HorizontalIndicatorProperties(
                                    textStyle = TextStyle.Default.copy(color = White)
                                )
                            )
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(top = MyAccountsTheme.dimensions.padding8),
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = null,
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
