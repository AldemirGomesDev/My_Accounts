package br.com.aldemir.recipe.presentation.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.model.CardType
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.usecase.recipe.DeleteRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipePerMonthUseCase
import br.com.aldemir.recipe.mapper.toDomain
import br.com.aldemir.recipe.mapper.toRecipeView
import br.com.aldemir.recipe.model.RecipeView
import br.com.aldemir.recipe.presentation.list.action.ListRecipeAction
import br.com.aldemir.recipe.presentation.list.model.ListRecipeUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.dialog_delete_title
import myaccounts.common.generated.resources.recipe_detail_screen_title

class ListRecipeViewModel(
    private val getAllRecipePerMonthUseCase: GetAllRecipePerMonthUseCase,
    private val getAllRecipeMonthlyUseCase: GetAllRecipeMonthlyUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ListRecipeUiModel())
    var uiModel = _uiModel.asStateFlow()

    fun handleAction(action: ListRecipeAction) {
        when (action) {
            is ListRecipeAction.LoadRecipes -> {
                getItemsMenu()
                getAllRecipePerMonth(DateUtils.getMonthString(), DateUtils.getYearString())
                getAllRecipeMonthly(DateUtils.getMonthString(), DateUtils.getYearString())
            }
            is ListRecipeAction.DeleteRecipe -> {
                delete(action.expense)
                getAllRecipePerMonth(DateUtils.getMonthString(), DateUtils.getYearString())
                updateShowDialog(false)
            }
            is ListRecipeAction.ShowDialog -> {
                updateShowDialog(action.showDialog)
            }
        }
    }

    private fun delete(expense: RecipeView) = viewModelScope.launch {
        deleteRecipeUseCase(this, expense.toDomain()) {
            success = { expenseId ->
                if (expenseId > 0) {
                    getAllRecipeMonthly(DateUtils.getMonthString(), DateUtils.getYearString())
                }
            }
        }
    }

    private fun getAllRecipeMonthly(month: String, year: String) = viewModelScope.launch {
        getAllRecipeMonthlyUseCase(this, GetAllRecipeMonthlyUseCase.Params(month, year)) {
            success = { recipesMonthlyDomain ->
                _uiModel.update {
                    it.copy(recipeMonthlyDomain = recipesMonthlyDomain)
                }
                calculateValues()
            }
            error = {
                calculateValues()
            }
        }
    }

    private fun getAllRecipePerMonth(month: String, year: String) = viewModelScope.launch {
        getAllRecipePerMonthUseCase(this, GetAllRecipePerMonthUseCase.Params(month, year)) {
            success = { listExpensePerMonth ->
                convertToRecipeView(listExpensePerMonth)
            }
        }
    }

    private fun convertToRecipeView(expensesPerMonth: List<RecipePerMonthDomain>) {
        val recipeViews = expensesPerMonth.map { expense ->
            expense.toRecipeView(
                checkIfExpired(
                    DateUtils.getCurrentDay(),
                    expense.due_date
                )
            )
        }

        _uiModel.update { current ->
            current.copy(recipes = recipeViews)
        }
    }

    private fun calculateValues() {
        clearValues()

        val recipes = _uiModel.value.recipeMonthlyDomain
        val valueTotal = recipes.sumOf { it.value }
        val paidOut = recipes.filter { it.status }.sumOf { it.value }
        val pending = recipes.filter { !it.status }.sumOf { it.value }

        val cardState = CardState(
            valueTotal = valueTotal,
            paidOut = paidOut,
            pending = pending
        )

        calculatePercentage(paidOut, valueTotal, cardState)
    }

    private fun clearValues() {
        _uiModel.update {
            it.copy(cardState = CardState())
        }
    }

    private fun calculatePercentage(paidOut: Double, valueTotal: Double, cardState: CardState) {
        val percentage = ((paidOut / valueTotal) * 100).toFloat()
        cardState.percentage = percentage
        cardState.cardType = CardType.RECIPE
        _uiModel.update {
            it.copy(cardState = cardState)
        }
    }

    private fun checkIfExpired(currentDay: Int, dueDay: Int): Boolean {
        return currentDay > dueDay
    }

    private fun getItemsMenu() {
        _uiModel.update {
            it.copy(
                menuItems = listOf(
                    DropdownItemState(
                        type = DropdownItemType.UPDATE,
                        titleRes = Res.string.recipe_detail_screen_title,
                        icon = Icons.Default.Edit,
                    ),
                    DropdownItemState(
                        type = DropdownItemType.DELETE,
                        titleRes = Res.string.dialog_delete_title,
                        icon = Icons.Default.Delete,
                    ),
                )
            )
        }
    }

    private fun updateShowDialog(show: Boolean) {
        _uiModel.update {
            it.copy(showDialog = show)
        }
    }
}