package br.com.aldemir.myaccounts.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth.GetAllExpensesMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth.GetAllRecipeMonthUseCase
import br.com.aldemir.myaccounts.presentation.home.model.HomeCardData
import br.com.aldemir.myaccounts.presentation.home.model.MonthValue
import br.com.aldemir.myaccounts.presentation.theme.LowPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.MediumPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.White
import br.com.aldemir.myaccounts.util.DateUtils
import br.com.aldemir.myaccounts.util.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllRecipeMonthUseCase: GetAllRecipeMonthUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
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
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val expenses = getAllExpensesMonthUseCase(month, years[index])
            setMonthValuesExpense(expenses)
        }
        setValuesExpenseToChart()
    }

    fun getAllRecipesSixMonthsPrevious() = viewModelScope.launch {
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val recipes = getAllRecipeMonthUseCase(month, years[index])
            setMonthValuesRecipe(recipes)
        }
        setValuesRecipeToChart()
    }

    private fun setMonthValuesExpense(expenses: List<MonthlyPayment>) {
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

    private fun setMonthValuesRecipe(recipes: List<RecipeMonthly>) {
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
        _monthValuesExpense.forEach {
            bars.add(
                BarChartData.Bar(
                    label = it.month.substring(0, 3),
                    value = it.value.toFloat(),
                    color = MediumPriorityColor,
                ),
            )
        }
        _barChartDataExpense.value = BarChartData(bars = bars.toList())
    }

    private fun setValuesRecipeToChart() {
        val bars = arrayListOf<BarChartData.Bar>()
        _monthValuesRecipe.forEach {
            bars.add(
                BarChartData.Bar(
                    label = it.month.substring(0, 3),
                    value = it.value.toFloat(),
                    color = LowPriorityColor,
                ),
            )
        }
        _barChartDataRecipe.value = BarChartData(bars = bars.toList())
    }


    private fun calculateValues(recipes: List<RecipeMonthly>, expenses: List<MonthlyPayment>) {
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