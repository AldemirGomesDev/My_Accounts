package br.com.aldemir.home.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.DateUtils.getMonthByLanguage
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.domain.base.awaitForResult
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase.Params
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthUseCase
import br.com.aldemir.home.presentation.action.HomeAction
import br.com.aldemir.home.presentation.model.BarChart
import br.com.aldemir.home.presentation.model.HomeCardData
import br.com.aldemir.home.presentation.model.MonthValue
import br.com.aldemir.home.presentation.state.HomeUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllRecipeMonthUseCase: GetAllRecipeMonthUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var _monthValuesExpense = mutableListOf<MonthValue>()
    private var _monthValuesRecipe = mutableListOf<MonthValue>()

    fun handleAction(event: HomeAction) {
        when (event) {
            is HomeAction.FetchData -> {
                getAllRecipeAndExpense()
                getAllExpenseSixMonthsPrevious()
                getAllRecipesSixMonthsPrevious()
            }
        }
    }

    private fun getAllRecipeAndExpense() = viewModelScope.launch {
        val month = DateUtils.getMonthString()
        val year = DateUtils.getYearString()

        val recipesDeferred = async {
            getAllRecipeMonthUseCase.awaitForResult(GetAllRecipeMonthUseCase.Params(month, year))
        }

        val expensesDeferred = async {
            getAllExpensesMonthUseCase.awaitForResult( Params(month, year))
        }
        val recipes = recipesDeferred.await()
        val expenses = expensesDeferred.await()

        calculateValues(recipes, expenses)
    }

    private fun getAllExpenseSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesExpense = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val params = Params(month, years[index])
            val expenses = getAllExpensesMonthUseCase.awaitForResult(params)
            setMonthValuesExpense(expenses)

        }
        setValuesExpenseToChart()
    }

    private fun getAllRecipesSixMonthsPrevious() = viewModelScope.launch {
        _monthValuesRecipe = mutableListOf()
        val months = DateUtils.getSixMonthsPrevious()
        val years = DateUtils.getYearsFromSixMonthsPrevious()
        months.forEachIndexed { index, month ->
            val recipes = getAllRecipeMonthUseCase.awaitForResult(
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
        val expenseBars = _monthValuesExpense
            .filter { it.month.isNotEmpty() }
            .map {
                BarChart(
                    label = it.month.take(3),
                    value = it.value,
                    color = MediumPriorityColor
                )
            }

        val missingBarsCount = (6 - expenseBars.size).coerceAtLeast(0)
        val monthsDropLast = months.dropLast(expenseBars.size)

        val emptyBars = monthsDropLast
            .takeLast(missingBarsCount)
            .map { month ->
                BarChart(
                    label = getMonthByLanguage(month).take(3),
                    value = 0.0,
                    color = MediumPriorityColor
                )
            }

        val bars = (emptyBars + expenseBars).takeLast(6)

        _uiState.update {
            HomeUiState.ShowHomeCards(
                it.uiModel.copy(barChartDataExpenses = bars)
            )
        }
    }

    private fun setValuesRecipeToChart() {
        val months = DateUtils.getSixMonthsPrevious()

        val recipeBars = _monthValuesRecipe
            .filter { it.month.isNotEmpty() }
            .map {
                BarChart(
                    label = it.month.take(3),
                    value = it.value,
                    color = LowPriorityColor
                )
            }

        val missingBarsCount = (6 - recipeBars.size).coerceAtLeast(0)
        val monthsDropLast = months.dropLast(recipeBars.size)

        val emptyBars = monthsDropLast
            .takeLast(missingBarsCount)
            .map { month ->
                BarChart(
                    label = getMonthByLanguage(month).take(3),
                    value = 0.0,
                    color = LowPriorityColor
                )
            }

        val bars = (emptyBars + recipeBars).takeLast(6)

        _uiState.update {
            HomeUiState.ShowHomeCards(
                it.uiModel.copy(barChartDataRecipes = bars)
            )
        }
    }

    private fun calculateValues(
        recipes: List<RecipeMonthlyDomain>,
        expenses: List<ExpenseMonthlyDomain>
    ) {
        val valueRecipe = recipes.sumOf { it.value }
        val valueExpense = expenses.sumOf { it.value }
        val valueBalance = valueRecipe - valueExpense

        updateHomeCardData(
            HomeCardData(
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
}
