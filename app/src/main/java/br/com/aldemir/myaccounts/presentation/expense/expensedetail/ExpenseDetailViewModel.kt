package br.com.aldemir.myaccounts.presentation.expense.expensedetail

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.mapper.toDatabase
import br.com.aldemir.myaccounts.domain.mapper.toView
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly.GetAllByIdExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.update.UpdateMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.presentation.shared.model.MonthlyPaymentView
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

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
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
        val id = updateMonthlyPaymentUseCase(monthlyPayment.toDatabase())
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
        else R.string.expense_pending
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}