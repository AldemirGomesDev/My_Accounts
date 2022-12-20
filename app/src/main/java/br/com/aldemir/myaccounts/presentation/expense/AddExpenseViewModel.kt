package br.com.aldemir.myaccounts.presentation.expense

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.AddExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.AddMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.util.Const.TAG
import br.com.aldemir.myaccounts.util.DateUtils
import br.com.aldemir.myaccounts.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val addMonthlyPaymentUseCase: AddMonthlyPaymentUseCase
    ) : ViewModel() {

    private val _addAccountFormState = MutableLiveData<AddExpenseFormState>()
    val addExpenseFormState: LiveData<AddExpenseFormState> = _addAccountFormState

    val id: MutableState<Int> = mutableStateOf(0)
    val name: MutableState<String> = mutableStateOf("")
    val value: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")

    private val _name = MutableStateFlow("")
    private val _value = MutableStateFlow(0.0)
    private val _description = MutableStateFlow("")
    private val _year = MutableStateFlow("")
    private val _months = MutableStateFlow(false)

    fun addAccount() = viewModelScope.launch {
        val expense = Expense(name = name.value, description = description.value, created_at = DateUtils.getDate(), due_date = 10)
        val idExpense = addExpenseUseCase(expense)
        id.value = idExpense.toInt()
        for (month in listOf("1 - JANEIRO", "11 - NOVEMBRO", "12 - DEZEMBRO")){
            val monthlyPayment = MonthlyPayment(
                id_expense = idExpense.toInt(),
                year = "2022",
                month = month,
                value = value.value.fromCurrency(),
                situation = false
            )
            insertMonthlyPayment(monthlyPayment)
        }
    }

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
        id.value = idExpense.toInt()
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
        Log.i(TAG, "addAccount - idMonthlyPayment: $idMonthlyPayment")
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
            _addAccountFormState.value = AddExpenseFormState(nameError  = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddExpenseFormState(nameError = null)
        }
    }

    fun setValue(value: Double) {
        _value.value = value
        if (!isValueValid(value)) {
            _addAccountFormState.value = AddExpenseFormState(valueError = R.string.invalid_value)
        } else {
            _addAccountFormState.value = AddExpenseFormState(valueError = null)
        }
    }

    fun setDescription(description: String) {
        _description.value = description
        if (!isDescriptionValid(description)) {
            _addAccountFormState.value = AddExpenseFormState(descriptionError = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddExpenseFormState(descriptionError = null)
        }
    }

    fun setYear(year: String) {
        _year.value = year
        if (!isYearValid(year)) {
            _addAccountFormState.value = AddExpenseFormState(yearError = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddExpenseFormState(yearError = null)
        }
    }

    fun setMonths(months: List<String>) {
        if (!isMonthsValid(months)) {
            _months.value = false
            _addAccountFormState.value = AddExpenseFormState(monthsError = R.string.invalid_name)
        } else {
            _months.value = true
            _addAccountFormState.value = AddExpenseFormState(monthsError = null)
        }
    }

}