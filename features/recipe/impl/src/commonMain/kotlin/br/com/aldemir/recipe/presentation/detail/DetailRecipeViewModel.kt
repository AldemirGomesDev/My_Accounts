package br.com.aldemir.recipe.presentation.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.model.DropdownItemState
import br.com.aldemir.common.model.DropdownItemType
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.domain.usecase.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeMonthlyUseCase
import br.com.aldemir.recipe.mapper.toView
import br.com.aldemir.recipe.mapper.viewToDomain
import br.com.aldemir.recipe.model.RecipeMonthlyView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.button_text_pay
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_paid_out
import org.jetbrains.compose.resources.StringResource

class DetailRecipeViewModel(
    private val getAllByIdRecipeUseCase: GetAllByIdRecipeUseCase,
    private val updateRecipeMonthlyUseCase: UpdateRecipeMonthlyUseCase
): ViewModel() {
    private val _recipeMonthlyView = MutableStateFlow<List<RecipeMonthlyView>>(emptyList())
    var recipeMonthlyView: StateFlow<List<RecipeMonthlyView>> = _recipeMonthlyView

    private val _menuItemsState = MutableStateFlow<List<DropdownItemState>>(listOf())
    val menuItemsState: StateFlow<List<DropdownItemState>> = _menuItemsState.asStateFlow()

    private val _id = MutableStateFlow<Int>(0)
    val id: StateFlow<Int> = _id

    private val _name = MutableStateFlow(emptyString())
    val name: StateFlow<String> = _name

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun getAllByIdRecipeMonthly(id: Int) = viewModelScope.launch {
        val monthlyPaymentViewList: MutableList<RecipeMonthlyView> = mutableListOf()
        getAllByIdRecipeUseCase(this, id) {
            success = { monthlyPaymentDomain ->
                monthlyPaymentDomain.forEach { item ->
                    monthlyPaymentViewList.add(item.toView(checkIfExpired(item.due_date, item.month, item.year)))
                }
                _name.value = monthlyPaymentViewList[0].name
                _recipeMonthlyView.update { monthlyPaymentViewList }
            }
        }
    }

    fun updateRecipeMonthly(recipeMonthlyView: RecipeMonthlyView) = viewModelScope.launch {
        _id.update { 0 }
        updateRecipeMonthlyUseCase(this, recipeMonthlyView.viewToDomain()) {
            success = { id ->
                _id.update { id }
            }
        }
    }

    private fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
        return (year == DateUtils.getYearString() && month == DateUtils.getMonthString() && DateUtils.getCurrentDay() > dueDay)
    }

    fun getStatusColor(status: Boolean, expired: Boolean): Color {
        return if (status) LowPriorityColor
        else if (expired) HighPriorityColor
        else MediumPriorityColor
    }

    fun getStatusText(status: Boolean, expired: Boolean): StringResource {
        return if (status) Res.string.expense_paid_out
        else if (expired) Res.string.expense_expired
        else Res.string.account_pending
    }

    fun getItemsMenu(){
        _menuItemsState.update {
            listOf(
                DropdownItemState(
                    type = DropdownItemType.PAY,
                    titleRes = Res.string.button_text_pay,
                    icon = Icons.Default.Check,
                ),
            )
        }
    }
}