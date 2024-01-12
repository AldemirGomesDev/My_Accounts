package br.com.aldemir.expense.presentation.expensedetail

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.domain.usecase.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.domain.usecase.expense.UpdateMonthlyPaymentUseCase
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.expense.mapper.toDomain
import br.com.aldemir.expense.mapper.toView
import br.com.aldemir.expense.model.MonthlyPaymentView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ExpenseDetailViewModel constructor(
    private val updateMonthlyPaymentUseCase: UpdateMonthlyPaymentUseCase,
    private val getAllByIdExpenseUseCase: GetAllByIdExpenseUseCase
    ) : ViewModel()  {

    companion object {
        const val TAG = "ExpenseDetailFragment"
    }

    private val _monthlyPayment = MutableStateFlow<List<MonthlyPaymentView>>(emptyList())
    var monthlyPayment: StateFlow<List<MonthlyPaymentView>> = _monthlyPayment

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

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
        val monthlyPaymentDomain = getAllByIdExpenseUseCase(id)
        monthlyPaymentDomain.forEach { item ->
            monthlyPaymentViewList.add(item.toView(checkIfExpired(item.due_date, item.month, item.year)))
        }
        _monthlyPayment.value = monthlyPaymentViewList
    }

    fun updateMonthlyPayment(monthlyPayment: MonthlyPaymentView) = viewModelScope.launch {
        _id.postValue(0)
        val id = updateMonthlyPaymentUseCase(monthlyPayment.toDomain())
        _id.postValue(id)
    }

    private fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
        return (year == DateUtils.getYear() && month == DateUtils.getMonth() && DateUtils.getDay() > dueDay)
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

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}