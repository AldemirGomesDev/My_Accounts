package br.com.aldemir.myaccounts.presentation.recipe.list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.mapper.toRecipeView
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe.GetAllRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth.GetAllRecipePerMonthUseCase
import br.com.aldemir.myaccounts.presentation.shared.model.CardState
import br.com.aldemir.myaccounts.presentation.shared.model.CardType
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeView
import br.com.aldemir.myaccounts.presentation.theme.HighPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.LowPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.MediumPriorityColor
import br.com.aldemir.myaccounts.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "listRecipeViewModel"

@HiltViewModel
class ListRecipeViewModel @Inject constructor(
    private val getAllRecipeUseCase: GetAllRecipeUseCase,
    private val getAllRecipePerMonthUseCase: GetAllRecipePerMonthUseCase,
    private val getAllRecipeMonthlyUseCase: GetAllRecipeMonthlyUseCase
) : ViewModel() {

    private val _recipeMonthlys = MutableStateFlow<List<RecipeMonthly>>(emptyList())

    private val _recipes = MutableLiveData<List<RecipeView>>(emptyList())
    var recipes: LiveData<List<RecipeView>> = _recipes

    private var _cardState = MutableStateFlow(CardState())
    val cardState: StateFlow<CardState> = _cardState.asStateFlow()

    fun getAllRecipe() = viewModelScope.launch {
        val recipes = getAllRecipeUseCase()
    }

    fun getAllRecipeMonthly(month: String, year: String) = viewModelScope.launch {
        _recipeMonthlys.value = getAllRecipeMonthlyUseCase(month, year)
        calculateValues()
    }

    fun getAllRecipePerMonth(month: String, year: String) = viewModelScope.launch {
        val expensePerMonth = getAllRecipePerMonthUseCase(month, year)
        convertToRecipeView(expensePerMonth)
    }

    private fun convertToRecipeView(expensesPerMonth: List<RecipePerMonth>) {
        val recipeViews = ArrayList<RecipeView>()
        expensesPerMonth.forEach { expensePerMonth ->
            val expense = expensePerMonth.toRecipeView(
                checkIfExpired(
                    DateUtils.getDay(),
                    expensePerMonth.due_date
                )
            )
            recipeViews.add(expense)
        }
        _recipes.value = recipeViews.toList()
    }

    private fun calculateValues() {
        clearValues()
        var valueTotal = 0.0
        var paidOut = 0.0
        var pending = 0.0
        var cardState = CardState()
        for (item in _recipeMonthlys.value) {
            cardState = CardState()
            valueTotal += item.value
            if (item.status) {
                paidOut += item.value
            } else {
                pending += item.value
            }
            cardState = cardState.copy(valueTotal = valueTotal, paidOut = paidOut, pending = pending)
        }
        calculatePercentage(paidOut, valueTotal, cardState)
    }

    private fun clearValues() {
       _cardState.value = CardState()
    }

    private fun calculatePercentage(paidOut: Double, valueTotal: Double, cardState: CardState) {
        val percentage = ((paidOut / valueTotal) * 100).toFloat()
        cardState.percentage = percentage
        cardState.cardType = CardType.RECIPE
        _cardState.value = cardState
    }

    private fun checkIfExpired(currentDay: Int, dueDay: Int): Boolean {
        return currentDay > dueDay
    }

    fun getStatusColor(status: Boolean, expired: Boolean): Color {
        return if (status) LowPriorityColor
        else if (expired) HighPriorityColor
        else MediumPriorityColor
    }

    fun getStatusText(status: Boolean, expired: Boolean): Int {
        return if (status) R.string.expense_paid_out
        else if (expired) R.string.expense_expired
        else R.string.expense_pending
    }
}