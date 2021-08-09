package br.com.aldemir.myaccounts.ui.expensechange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.ui.expense.AddAccountFormState

class ChangeExpenseViewModel(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
) : ViewModel() {
    companion object {
        private const val TAG = "ChangeDetailFragment"
    }

    private val _changeFormState = MutableLiveData<AddAccountFormState>()
    val changeFormState: LiveData<AddAccountFormState> = _changeFormState

    private val _monthlyPayment = MutableLiveData<MonthlyPayment>()
    var monthlyPayment: LiveData<MonthlyPayment> = _monthlyPayment

    private val _isValidForm = MutableLiveData(false)
    val isValidForm: LiveData<Boolean> = _isValidForm

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    fun getAllByIdMonthlyPayment(id: Int) {
        monthlyPayment = monthlyPaymentRepository.getByIdMonthlyPayment(id)
    }

    fun updateMonthlyPayment(monthlyPayment: MonthlyPayment) {
        _id.value = monthlyPaymentRepository.update(monthlyPayment)
    }

    fun setValue(value: Double) {
        Log.d(TAG, "isValueValid: ${!isValueValid(value)}")
        _isValidForm.value = isValueValid(value)
        if (!isValueValid(value)) {
            _changeFormState.value = AddAccountFormState(valueError = R.string.invalid_value)
        } else {
            _changeFormState.value = AddAccountFormState(valueError = null)
        }
    }

    private fun isValueValid(value: Double): Boolean {
        return value != 0.0
    }
}