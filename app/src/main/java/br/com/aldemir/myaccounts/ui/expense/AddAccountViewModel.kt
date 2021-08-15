package br.com.aldemir.myaccounts.ui.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.AddExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.AddMonthlyPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val addMonthlyPaymentUseCase: AddMonthlyPaymentUseCase
    ) : ViewModel() {

    companion object {
        private const val TAG = "AddAccountFragment"
    }

    private val _addAccountFormState = MutableLiveData<AddAccountFormState>()
    val addAccountFormState: LiveData<AddAccountFormState> = _addAccountFormState

    private val _name = MutableStateFlow("")
    private val _value = MutableStateFlow(0.0)
    private val _description = MutableStateFlow("")
    private val _year = MutableStateFlow("")
    private val _months = MutableStateFlow(false)

    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long> = _id

    fun insertExpense(
        name: String,
        description: String,
        year: String,
        months: List<String>,
        value: Double,
        situation: Boolean,
        createdAt: Date?,
        dueDate: Int
    ) = viewModelScope.launch {
        val expense = Expense(name = name,description = description, created_at = createdAt, due_date = dueDate)
        val idExpense = addExpenseUseCase(expense)
        _id.value = idExpense
        for (month in months) {
            val monthlyPayment = MonthlyPayment(
                id_expense = idExpense.toInt(),
                year = year,
                month = month,
                value = value,
                situation = situation,
            )
            insertMonthlyPayment(monthlyPayment)
        }
    }

    private fun insertMonthlyPayment(monthlyPayment: MonthlyPayment) = viewModelScope.launch {
        val idMonthlyPayment = addMonthlyPaymentUseCase(monthlyPayment)
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_name, _value, _description, _year, _months) {
            name, value, description, year, months ->
        val isNameCorrect = isNameValid(name)
        val isValueCorrect = isValueValid(value)
        val isDescriptionCorrect = isDescriptionValid(description)
        val isYear = isYearValid(year)
        return@combine isNameCorrect and isValueCorrect and isDescriptionCorrect and isYear and months
    }

    private fun isNameValid(name: String): Boolean {
        return name.length > 3
    }

    private fun isValueValid(value: Double): Boolean {
        return value != 0.0
    }

    private fun isDescriptionValid(description: String): Boolean {
        return description.length > 3
    }

    private fun isYearValid(year: String): Boolean {
        return year.isNotEmpty()
    }

    private fun isMonthsValid(months: List<String>): Boolean {
        return months.isNotEmpty()
    }

    fun setName(name: String) {
        _name.value = name
        if (!isNameValid(name)) {
            _addAccountFormState.value = AddAccountFormState(nameError  = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddAccountFormState(nameError = null)
        }
    }

    fun setValue(value: Double) {
        _value.value = value
        if (!isValueValid(value)) {
            _addAccountFormState.value = AddAccountFormState(valueError = R.string.invalid_value)
        } else {
            _addAccountFormState.value = AddAccountFormState(valueError = null)
        }
    }

    fun setDescription(description: String) {
        _description.value = description
        if (!isDescriptionValid(description)) {
            _addAccountFormState.value = AddAccountFormState(descriptionError = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddAccountFormState(descriptionError = null)
        }
    }

    fun setYear(year: String) {
        _year.value = year
        if (!isYearValid(year)) {
            _addAccountFormState.value = AddAccountFormState(yearError = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddAccountFormState(yearError = null)
        }
    }

    fun setMonths(months: List<String>) {
        if (!isMonthsValid(months)) {
            _months.value = false
            _addAccountFormState.value = AddAccountFormState(monthsError = R.string.invalid_name)
        } else {
            _months.value = true
            _addAccountFormState.value = AddAccountFormState(monthsError = null)
        }
    }

}