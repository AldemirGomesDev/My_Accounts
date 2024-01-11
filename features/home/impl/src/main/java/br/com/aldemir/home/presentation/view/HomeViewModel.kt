package br.com.aldemir.home.presentation.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.publ.domain.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth.GetAllRecipeMonthUseCase
import br.com.aldemir.home.presentation.model.HomeCardData
import br.com.aldemir.home.presentation.model.MonthValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer

class HomeViewModel constructor(
    private val getAllRecipeMonthUseCase: GetAllRecipeMonthUseCase,
    private val getAllExpensesMonthUseCase: br.com.aldemir.publ.domain.expense.GetAllExpensesMonthUseCase,
) : ViewModel() {

    private val bar = BarChartData.Bar(
        label = emptyString(),
        value = 0F,
        color = Color(0XFFFFC107),
    )

    private var _homeCardData = MutableStateFlow(HomeCardData())
    val homeCardData: StateFlow<HomeCardData> = _homeCardData.asStateFlow()

    private var _barChartDataExpense = MutableStateFlow(BarChartData(bars = listOf(bar)))
    val barChartDataExpense: StateFlow<BarChartData> = _barChartDataExpense.asStateFlow()

    private var _barChartDataRecipe = MutableStateFlow(BarChartData(bars = listOf(bar)))
    val barChartDataRecipe: StateFlow<BarChartData> = _barChartDataRecipe.asStateFlow()

    private var _monthValuesExpense = mutableListOf<MonthValue>()
    private var _monthValuesRecipe = mutableListOf<MonthValue>()

    fun getAllRecipeAndExpense() = viewModelScope.launch {
        val recipes = getAllRecipeMonthUseCase(DateUtils.getMonth(), DateUtils.getYear())
        val expenses = getAllExpensesMonthUseCase(DateUtils.getMonth(), DateUtils.getYear())
        calculateValues(recipes, expenses)
    }

    fun getAllExpenseSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesExpense = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val expenses = getAllExpensesMonthUseCase(month, years[index])
            setMonthValuesExpense(expenses)
        }
        setValuesExpenseToChart()
    }

    fun getAllRecipesSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesRecipe = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val recipes = getAllRecipeMonthUseCase(month, years[index])
            setMonthValuesRecipe(recipes)
        }
        setValuesRecipeToChart()
    }

    private fun setMonthValuesExpense(expenses: List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO>) {
        var valueExpense = 0.0
        var monthExpense = emptyString()
        expenses.forEach { expense ->
            valueExpense += expense.value
            monthExpense = expense.month
        }
        _monthValuesExpense.add(
            MonthValue(
                month = monthExpense,
                value = valueExpense
            )
        )
    }

    private fun setMonthValuesRecipe(recipes: List<br.com.aldemir.data.database.model.RecipeMonthlyDTO>) {
        var valueRecipe = 0.0
        var monthRecipe = emptyString()
        recipes.forEach { recipe ->
            valueRecipe += recipe.value
            monthRecipe = recipe.month
        }
        _monthValuesRecipe.add(
            MonthValue(
                month = monthRecipe,
                value = valueRecipe
            )
        )
    }

    private fun setValuesExpenseToChart() {
        val bars = arrayListOf<BarChartData.Bar>()
        val maxBar = 6
        val rest = maxBar - _monthValuesExpense.size
        for (i in 0 until rest){
            bars.add(
                BarChartData.Bar(
                    label = emptyString(),
                    value = 0F,
                    color = MediumPriorityColor,
                ),
            )
        }
        _monthValuesExpense.forEach {
            if (it.month.isNotEmpty()) {
                bars.add(
                    BarChartData.Bar(
                        label = it.month.substring(0, 3),
                        value = it.value.toFloat(),
                        color = MediumPriorityColor,
                    ),
                )
            }
        }
        if (bars.isNotEmpty()) {
            _barChartDataExpense.value = BarChartData(bars = bars.toList())
        }
    }

    private fun setValuesRecipeToChart() {
        val bars = arrayListOf<BarChartData.Bar>()
        val maxBar = 6
        val rest = maxBar - _monthValuesRecipe.size
        for (i in 0 until rest){
            bars.add(
                BarChartData.Bar(
                    label = emptyString(),
                    value = 0F,
                    color = MediumPriorityColor,
                ),
            )
        }
        _monthValuesRecipe.forEach {
            if (it.month.isNotEmpty()) {
                bars.add(
                    BarChartData.Bar(
                        label = it.month.substring(0, 3),
                        value = it.value.toFloat(),
                        color = LowPriorityColor,
                    ),
                )
            }
        }
        if (bars.isNotEmpty()) {
            _barChartDataRecipe.value = BarChartData(bars = bars.toList())
        }
    }


    private fun calculateValues(recipes: List<br.com.aldemir.data.database.model.RecipeMonthlyDTO>, expenses: List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO>) {
        _homeCardData.value = HomeCardData()
        var valueRecipe = 0.0
        var valueExpense = 0.0
        var valueBalance = 0.0
        recipes.forEach { recipe ->
            valueRecipe += recipe.value
        }
        expenses.forEach { expense ->
            valueExpense += expense.value
        }
        valueBalance = (valueRecipe - valueExpense)
        _homeCardData.value = HomeCardData(
            valueRecipe = valueRecipe,
            valueExpense = valueExpense,
            valueBalance = valueBalance
        )
    }

    var labelDrawer by mutableStateOf(
        SimpleLabelDrawer(
            drawLocation = SimpleLabelDrawer.DrawLocation.Inside,
            labelTextColor = White
        )
    )


    private var colors = mutableListOf(
        Color(0XFFF44336),
        Color(0XFFE91E63),
        Color(0XFF9C27B0),
        Color(0XFF673AB7),
        Color(0XFF3F51B5),
        Color(0XFF03A9F4),
        Color(0XFF009688),
        Color(0XFFCDDC39),
        Color(0XFFFFC107),
        Color(0XFFFF5722),
        Color(0XFF795548),
        Color(0XFF9E9E9E),
        Color(0XFF607D8B)
    )

}