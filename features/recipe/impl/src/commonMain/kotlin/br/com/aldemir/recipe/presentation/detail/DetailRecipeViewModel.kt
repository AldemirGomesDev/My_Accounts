package br.com.aldemir.recipe.presentation.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.usecase.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeSituationUseCase
import br.com.aldemir.recipe.mapper.toView
import br.com.aldemir.recipe.model.RecipeMonthlyView
import br.com.aldemir.recipe.presentation.detail.action.DetailRecipeAction
import br.com.aldemir.recipe.presentation.detail.model.DetailRecipeUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.button_text_pay

class DetailRecipeViewModel(
    private val getAllByIdRecipeUseCase: GetAllByIdRecipeUseCase,
    private val updateRecipeSituationUseCase: UpdateRecipeSituationUseCase
) : ViewModel() {
    private val _uiModel = MutableStateFlow(DetailRecipeUiModel())
    var uiModel = _uiModel.asStateFlow()

    fun handleAction(action: DetailRecipeAction) {
        when (action) {
            is DetailRecipeAction.FetchData -> {
                getAllByIdRecipeMonthly(action.id)
                getItemsMenu()
            }

            is DetailRecipeAction.UpdateRecipeMonthly -> {
                updateRecipeMonthly(action.id)
            }

            is DetailRecipeAction.UpdateShowDialog -> {
                onUpdateShowDialog(action.showDialog)
            }
        }
    }

    private fun onUpdateShowDialog(showDialog: Boolean) {
        _uiModel.update {
            it.copy(showDialog = showDialog)
        }
    }

    private fun getAllByIdRecipeMonthly(id: Int) = viewModelScope.launch {
        getAllByIdRecipeUseCase(this, id) {
            success = { recipeMonthlyDomainList ->
                val recipeViews = recipeMonthlyDomainList.map { item ->
                    item.toView(
                        checkIfExpired(item.due_date, item.month, item.year)
                    )
                }

                _uiModel.update { uiModel ->
                    uiModel.copy(
                        recipeId = id,
                        recipesMonthlyView = recipeViews,
                        name = recipeViews.firstOrNull()?.name.orEmpty()
                    )
                }
            }
        }
    }

    private fun updateRecipeMonthly(id: Int) = viewModelScope.launch {
        updateRecipeSituationUseCase(
            this,
            UpdateRecipeSituationUseCase.Params(id, true)
        ) {
            success = {
                getAllByIdRecipeMonthly(_uiModel.value.recipeId)
            }
        }
    }

    private fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
        return (year == DateUtils.getYearString() && month == DateUtils.getMonthString() && DateUtils.getCurrentDay() > dueDay)
    }

    private fun getItemsMenu() {
        _uiModel.update { uiModel ->
            uiModel.copy(
                menuItems = listOf(
                    DropdownItemState(
                        type = DropdownItemType.PAY,
                        titleRes = Res.string.button_text_pay,
                        icon = Icons.Default.Check,
                    ),
                )
            )
        }
    }
}