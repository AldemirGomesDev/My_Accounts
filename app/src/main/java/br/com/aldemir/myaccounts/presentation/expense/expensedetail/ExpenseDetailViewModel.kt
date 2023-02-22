package br.com.aldemir.myaccounts.presentation.expense.expensedetail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.MyApplication
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.GetAllByIdExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.UpdateMonthlyPaymentUseCase
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

    private val _monthlyPayment = MutableStateFlow<List<MonthlyPayment>>(emptyList())
    var monthlyPayment: StateFlow<List<MonthlyPayment>> = _monthlyPayment

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
        _monthlyPayment.value = getAllByIdExpenseUseCase(id)
    }

    fun updateMonthlyPayment(monthlyPayment: MonthlyPayment) = viewModelScope.launch {
        _id.postValue(0)
        val id = updateMonthlyPaymentUseCase(monthlyPayment)
        _id.postValue(id)
    }

    fun checkPaidOut(situation: Boolean): String {
        return if (situation) MyApplication.appContext.getString(R.string.expense_paid_out)
        else MyApplication.appContext.getString(R.string.expense_pending)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}