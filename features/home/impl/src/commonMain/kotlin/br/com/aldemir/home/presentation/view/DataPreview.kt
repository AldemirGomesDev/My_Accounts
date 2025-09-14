package br.com.aldemir.home.presentation.view

import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.home.presentation.model.HomeCardData
import br.com.aldemir.home.presentation.model.HomeUiModel
import br.com.aldemir.home.presentation.state.HomeUiState
import me.bytebeats.views.charts.bar.BarChartData

val homeDataPreview = HomeUiState.ShowHomeCards(
    uiModel = HomeUiModel(
        homeCardData = HomeCardData(
            valueRecipe = 2500.00,
            valueExpense = 1500.00
        ),
        barChartDataExpense = BarChartData(
            bars = listOf(
                BarChartData.Bar(value = 0.2f, label = "Jan", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.4f, label = "Fev", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.6f, label = "Mar", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.8f, label = "Abr", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.5f, label = "Mai", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.7f, label = "Jun", color = MediumPriorityColor),
            ),
        ),
        barChartDataRecipe = BarChartData(
            bars = listOf(
                BarChartData.Bar(value = 0.3f, label = "Jan", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.5f, label = "Fev", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.7f, label = "Mar", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.9f, label = "Abr", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.6f, label = "Mai", color = MediumPriorityColor),
                BarChartData.Bar(value = 0.7f, label = "Jun", color = MediumPriorityColor),
            ),
        )
    )
)