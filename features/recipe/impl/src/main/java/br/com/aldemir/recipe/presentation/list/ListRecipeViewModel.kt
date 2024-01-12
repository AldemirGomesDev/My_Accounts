package br.com.aldemir.recipe.presentation.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.domain.usecase.recipe.DeleteRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipePerMonthUseCase
import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.model.CardType
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.recipe.mapper.toDomain
import br.com.aldemir.recipe.mapper.toRecipeView
import br.com.aldemir.recipe.model.RecipeView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "listRecipeViewModel"

class ListRecipeViewModel constructor(
    private val getAllRecipePerMonthUseCase: GetAllRecipePerMonthUseCase,
    private val getAllRecipeMonthlyUseCase: GetAllRecipeMonthlyUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {

    private val _recipeMonthlyDomain = MutableStateFlow<List<RecipeMonthlyDomain>>(emptyList())

    private val _recipes = MutableLiveData<List<RecipeView>>(emptyList())
    var recipes: LiveData<List<RecipeView>> = _recipes

    private var _cardState = MutableStateFlow(CardState())
    val cardState: StateFlow<CardState> = _cardState.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private val _menuItemsState = MutableStateFlow<Array<DropdownItemState>>(arrayOf())
    val menuItemsState: StateFlow<Array<DropdownItemState>> = _menuItemsState.asStateFlow()

    fun delete(expense: RecipeView) = viewModelScope.launch {
        val expenseId = deleteRecipeUseCase(expense.toDomain())
        if (expenseId > 0) {
            getAllRecipeMonthly(DateUtils.getMonth(), DateUtils.getYear())
        }
    }

    fun getAllRecipeMonthly(month: String, year: String) = viewModelScope.launch {
        _recipeMonthlyDomain.value = getAllRecipeMonthlyUseCase(month, year)
        calculateValues()
    }

    fun getAllRecipePerMonth(month: String, year: String) = viewModelScope.launch {
        val expensePerMonth = getAllRecipePerMonthUseCase(month, year)
        convertToRecipeView(expensePerMonth)
    }

    private fun convertToRecipeView(expensesPerMonth: List<RecipePerMonthDomain>) {
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
        for (item in _recipeMonthlyDomain.value) {
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
        else R.string.account_pending
    }

    fun getItemsMenu() {
        _menuItemsState.value = arrayOf(
            DropdownItemState(
                type = DropdownItemType.UPDATE,
                titleRes = R.string.recipe_detail_screen_title,
                icon = Icons.Default.Edit,
            ),
            DropdownItemState(
                type = DropdownItemType.DELETE,
                titleRes = R.string.dialog_delete_title,
                icon = Icons.Default.Delete,
            ),
        )
    }

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }
}