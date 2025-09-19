package br.com.aldemir.expense.presentation.expensedetail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.usecase.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.domain.usecase.expense.UpdateMonthlyPaymentUseCase
import br.com.aldemir.expense.mapper.toDomain
import br.com.aldemir.expense.mapper.toView
import br.com.aldemir.expense.model.MonthlyPaymentView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_paid_out
import org.jetbrains.compose.resources.StringResource

class ExpenseDetailViewModel(
    private val updateMonthlyPaymentUseCase: UpdateMonthlyPaymentUseCase,
    private val getAllByIdExpenseUseCase: GetAllByIdExpenseUseCase
) : ViewModel() {

    companion object {
        const val TAG = "ExpenseDetailFragment"
    }

    private val _monthlyPayment = MutableStateFlow<List<MonthlyPaymentView>>(emptyList())
    var monthlyPayment: StateFlow<List<MonthlyPaymentView>> = _monthlyPayment

    private val _id = MutableStateFlow(0)
    val id: StateFlow<Int> = _id

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

    fun getAllByIdExpense(id: Int) = viewModelScope.launch {
        val monthlyPaymentViewList: MutableList<MonthlyPaymentView> = mutableListOf()
        getAllByIdExpenseUseCase(this, id).apply {
            onSuccess { monthlyPaymentDomain ->
                monthlyPaymentDomain.forEach { item ->
                    monthlyPaymentViewList.add(
                        item.toView(
                            checkIfExpired(
                                item.due_date,
                                item.month,
                                item.year
                            )
                        )
                    )
                }
                _monthlyPayment.value = monthlyPaymentViewList
            }
        }
    }

    fun updateMonthlyPayment(monthlyPayment: MonthlyPaymentView) = viewModelScope.launch {
        _id.update { 0 }
        updateMonthlyPaymentUseCase(this, monthlyPayment.toDomain()).apply {
            onSuccess { id -> _id.update { id } }
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
}