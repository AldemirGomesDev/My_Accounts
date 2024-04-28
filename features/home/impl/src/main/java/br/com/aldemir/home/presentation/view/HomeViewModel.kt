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
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase.Params
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthUseCase
import br.com.aldemir.home.presentation.model.HomeCardData
import br.com.aldemir.home.presentation.model.MonthValue
import br.com.aldemir.home.presentation.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.bar.BarChartData
import me.bytebeats.views.charts.bar.render.label.SimpleLabelDrawer

class HomeViewModel(
    private val getAllRecipeMonthUseCase: GetAllRecipeMonthUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private var _monthValuesExpense = mutableListOf<MonthValue>()
    private var _monthValuesRecipe = mutableListOf<MonthValue>()

    fun getAllRecipeAndExpense() = viewModelScope.launch {
        var expenses: List<ExpenseMonthlyDomain> = listOf()
        var recipes: List<RecipeMonthlyDomain> = listOf()
        val month = DateUtils.getMonth()
        val year = DateUtils.getYear()
        getAllRecipeMonthUseCase(
            this,
            GetAllRecipeMonthUseCase.Params(month, year)
        ).apply {
            onSuccess {
                recipes = it
            }
        }
        getAllExpensesMonthUseCase(this, Params(month, year)).apply {
            onSuccess {
                expenses = it
            }
        }
        calculateValues(recipes, expenses)
    }

    fun getAllExpenseSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesExpense = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val params = Params(month, years[index])
            getAllExpensesMonthUseCase(this, params).apply {
                onSuccess {
                    setMonthValuesExpense(it)
                }
            }

        }
        setValuesExpenseToChart()
    }

    fun getAllRecipesSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesRecipe = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            getAllRecipeMonthUseCase(
                this,
                GetAllRecipeMonthUseCase.Params(month, years[index])
            ).apply {
                onSuccess {
                    setMonthValuesRecipe(it)
                }
            }
        }
        setValuesRecipeToChart()
    }

    private fun setMonthValuesExpense(expenses: List<ExpenseMonthlyDomain>) {
        var valueExpense = 0.0
        var monthExpense = emptyString()
        expenses.forEach { expense ->
            valueExpense += expense.value
            monthExpense = expense.month.ifEmpty { emptyString() }
        }
        _monthValuesExpense.add(
            MonthValue(
                month = monthExpense,
                value = valueExpense
            )
        )
    }

    private fun setMonthValuesRecipe(recipes: List<RecipeMonthlyDomain>) {
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
        val months = DateUtils.getSixMonthsPrevious()
        val bars = arrayListOf<BarChartData.Bar>()
        val maxBar = 6
        val rest = maxBar - _monthValuesExpense.size
        for (i in 0 until rest) {
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
        val monthsDropLast = months.dropLast(bars.size)

        monthsDropLast.forEachIndexed { index, s ->
            bars.add(index,
                BarChartData.Bar(
                    label = s.ifEmpty { "MÊS" }.substring(0, 3),
                    value = 0f,
                    color = MediumPriorityColor,
                ),
            )
        }

        if (bars.isNotEmpty()) {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataExpense = BarChartData(bars = bars.toList()))
                )
            }
        } else {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataExpense = null)
                )
            }
        }
    }

    private fun setValuesRecipeToChart() {
        val months = DateUtils.getSixMonthsPrevious()
        val bars = arrayListOf<BarChartData.Bar>()
        val maxBar = 6
        val rest = maxBar - _monthValuesRecipe.size
        for (i in 0 until rest) {
            bars.add(
                BarChartData.Bar(
                    label = emptyString(),
                    value = 0F,
                    color = LowPriorityColor,
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
        val monthsDropLast = months.dropLast(bars.size)

        monthsDropLast.forEachIndexed { index, s ->
            bars.add(index,
                BarChartData.Bar(
                    label = s.ifEmpty { "MÊS" }.substring(0, 3),
                    value = 0f,
                    color = LowPriorityColor,
                ),
            )
        }

        if (bars.isNotEmpty()) {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataRecipe = BarChartData(bars = bars.toList()))
                )
            }
        } else {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataRecipe = null)
                )
            }
        }
    }


    private fun calculateValues(
        recipes: List<RecipeMonthlyDomain>,
        expenses: List<ExpenseMonthlyDomain>
    ) {
        updateHomeCardData(HomeCardData())
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
        updateHomeCardData(
            homeCardData = HomeCardData(
                valueRecipe = valueRecipe,
                valueExpense = valueExpense,
                valueBalance = valueBalance
            )
        )
    }

    private fun updateHomeCardData(homeCardData: HomeCardData) {
        val currentModel = checkNotNull(uiState.value.uiModel)
        _uiState.value = HomeUiState.ShowHomeCards(
            currentModel.copy(
                homeCardData = homeCardData
            )
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