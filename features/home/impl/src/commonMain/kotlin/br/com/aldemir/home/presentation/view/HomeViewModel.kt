package br.com.aldemir.home.presentation.view

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.domain.base.awaitForResult
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase.Params
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthUseCase
import br.com.aldemir.home.presentation.model.BarChart
import br.com.aldemir.home.presentation.model.HomeCardData
import br.com.aldemir.home.presentation.model.MonthValue
import br.com.aldemir.home.presentation.state.HomeUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllRecipeMonthUseCase: GetAllRecipeMonthUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private var _monthValuesExpense = mutableListOf<MonthValue>()
    private var _monthValuesRecipe = mutableListOf<MonthValue>()

    fun getAllRecipeAndExpense() = viewModelScope.launch {
        val month = DateUtils.getMonthString()
        val year = DateUtils.getYearString()

        val recipesDeferred = async {
            getAllRecipeMonthUseCase.awaitForResult(this, GetAllRecipeMonthUseCase.Params(month, year))
        }

        val expensesDeferred = async {
            getAllExpensesMonthUseCase.awaitForResult(this, Params(month, year))
        }
        val recipes = recipesDeferred.await()
        val expenses = expensesDeferred.await()

        calculateValues(recipes, expenses)
    }

    fun getAllExpenseSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesExpense = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val params = Params(month, years[index])
            val expenses = getAllExpensesMonthUseCase.awaitForResult(this, params)
            setMonthValuesExpense(expenses)

        }
        setValuesExpenseToChart()
    }

    fun getAllRecipesSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesRecipe = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val recipes = getAllRecipeMonthUseCase.awaitForResult(
                this,
                GetAllRecipeMonthUseCase.Params(month, years[index])
            )
            setMonthValuesRecipe(recipes)
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
        val bars = arrayListOf<BarChart>()
        val maxBar = 6
        val rest = maxBar - _monthValuesExpense.size
        for (i in 0 until rest) {
            bars.add(
                BarChart(
                    label = emptyString(),
                    value = 0.0,
                    color = MediumPriorityColor,
                ),
            )
        }

        _monthValuesExpense.forEach {
            if (it.month.isNotEmpty()) {
                bars.add(
                    BarChart(
                        label = it.month.substring(0, 3),
                        value = it.value,
                        color = MediumPriorityColor,
                    ),
                )
            }
        }
        val monthsDropLast = months.dropLast(bars.size)

        if (monthsDropLast.size < 6) {
            monthsDropLast.forEachIndexed { index, s ->
                bars.add(index,
                    BarChart(
                        label = s.ifEmpty { "MÊS" }.substring(0, 3),
                        value = 0.0,
                        color = MediumPriorityColor,
                    ),
                )
            }
        }

        if (bars.isNotEmpty()) {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataExpenses = bars)
                )
            }
        } else {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataExpenses = emptyList())
                )
            }
        }
    }

    private fun setValuesRecipeToChart() {
        val months = DateUtils.getSixMonthsPrevious()
        val bars = arrayListOf<BarChart>()
        val maxBar = 6
        val rest = maxBar - _monthValuesRecipe.size
        for (i in 0 until rest) {
            bars.add(
                BarChart(
                    label = emptyString(),
                    value = 0.0,
                    color = LowPriorityColor,
                ),
            )
        }
        _monthValuesRecipe.forEach {
            if (it.month.isNotEmpty()) {
                bars.add(
                    BarChart(
                        label = it.month.substring(0, 3),
                        value = it.value,
                        color = LowPriorityColor,
                    ),
                )
            }
        }
        val monthsDropLast = months.dropLast(bars.size)

        if (monthsDropLast.size < 6) {
            monthsDropLast.forEachIndexed { index, s ->
                bars.add(index,
                    BarChart(
                        label = s.ifEmpty { "MÊS" }.substring(0, 3),
                        value = 0.0,
                        color = LowPriorityColor,
                    ),
                )
            }
        }

        if (bars.isNotEmpty()) {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataRecipes = bars)
                )
            }
        } else {
            _uiState.update {
                HomeUiState.ShowHomeCards(
                    it.uiModel.copy(barChartDataRecipes = emptyList())
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
        _uiState.update {
            HomeUiState.ShowHomeCards(
                currentModel.copy(
                    homeCardData = homeCardData
                )
            )
        }
    }

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