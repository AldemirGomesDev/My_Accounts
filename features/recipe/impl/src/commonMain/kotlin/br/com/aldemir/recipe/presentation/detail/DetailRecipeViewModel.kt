package br.com.aldemir.recipe.presentation.detail

import android.content.Context
import android.widget.Toast
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

    private val _menuItemsState = MutableStateFlow<Array<DropdownItemState>>(arrayOf())
    val menuItemsState: StateFlow<Array<DropdownItemState>> = _menuItemsState.asStateFlow()

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
        getAllByIdRecipeUseCase(this, id).apply {
            onSuccess { monthlyPaymentDomain ->
                monthlyPaymentDomain.forEach { item ->
                    monthlyPaymentViewList.add(item.toView(checkIfExpired(item.due_date, item.month, item.year)))
                }
                _name.value = monthlyPaymentViewList[0].name
                _recipeMonthlyView.value = monthlyPaymentViewList
            }
        }
    }

    fun updateRecipeMonthly(recipeMonthlyView: RecipeMonthlyView) = viewModelScope.launch {
        _id.update { 0 }
        updateRecipeMonthlyUseCase(this, recipeMonthlyView.viewToDomain()).apply {
            onSuccess {id ->
                _id.update { id }
            }
        }
    }

    private fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
        return (year == DateUtils.getYearString() && month == DateUtils.getMonthString() && DateUtils.getDay() > dueDay)
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

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun getItemsMenu(){
        _menuItemsState.value = arrayOf(
            DropdownItemState(
                type = DropdownItemType.PAY,
                titleRes = Res.string.button_text_pay,
                icon = Icons.Default.Check,
            ),
        )
    }
}