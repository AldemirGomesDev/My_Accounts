package br.com.aldemir.myaccounts.ui.expensechange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.domain.usecase.UpdateMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.ui.expense.AddExpenseFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeExpenseViewModel @Inject constructor(
    private val updateMonthlyPaymentUseCase: UpdateMonthlyPaymentUseCase,
    private val getByIdMonthlyPaymentUseCase: GetByIdMonthlyPaymentUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "ChangeDetailFragment"
    }

    private val _changeFormState = MutableLiveData<AddExpenseFormState>()
    val changeFormState: LiveData<AddExpenseFormState> = _changeFormState

    private val _monthlyPayment = MutableLiveData<MonthlyPayment>()
    var monthlyPayment: LiveData<MonthlyPayment> = _monthlyPayment

    private val _isValidForm = MutableLiveData(false)
    val isValidForm: LiveData<Boolean> = _isValidForm

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    fun getAllByIdMonthlyPayment(id: Int) = viewModelScope.launch {
        _monthlyPayment.value = getByIdMonthlyPaymentUseCase(id)!!
    }

    fun updateMonthlyPayment(monthlyPayment: MonthlyPayment) = viewModelScope.launch {
        _id.value = updateMonthlyPaymentUseCase(monthlyPayment)!!
    }

    fun setValue(value: Double) {
        Log.d(TAG, "isValueValid: ${!isValueValid(value)}")
        _isValidForm.value = isValueValid(value)
        if (!isValueValid(value)) {
            _changeFormState.value = AddExpenseFormState(valueError = R.string.invalid_value)
        } else {
            _changeFormState.value = AddExpenseFormState(valueError = null)
        }
    }

    private fun isValueValid(value: Double): Boolean {
        return value != 0.0
    }
}