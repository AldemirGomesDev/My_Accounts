package br.com.aldemir.home.presentation.model

import androidx.compose.ui.graphics.Color
import br.com.aldemir.common.util.emptyString
import me.bytebeats.views.charts.bar.BarChartData

data class HomeUiModel(
    val homeCardData: HomeCardData = HomeCardData(),
    val barChartDataExpense: BarChartData? = null,
    val barChartDataRecipe: BarChartData? = null
)

fun getBar() = BarChartData.Bar(
    label = emptyString(),
    value = 0F,
    color = Color(0XFFFFC107),
)
