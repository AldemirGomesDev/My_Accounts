package br.com.aldemir.home.presentation.model

import androidx.compose.ui.graphics.Color

data class HomeUiModel(
    val homeCardData: HomeCardData = HomeCardData(),
    val barChartDataExpenses: List<BarChart> = listOf(),
    val barChartDataRecipes: List<BarChart> = listOf(),
)

data class BarChart (
    val label: String,
    val value: Double,
    val color: Color,
)
