package br.com.aldemir.myaccounts.presentation.addexpense

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
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    val name: MutableState<String> = mutableStateOf(emptyString())
    val isNameValid: MutableState<Boolean> = mutableStateOf(false)
    val nameError: MutableState<String> = mutableStateOf(emptyString())

    val value: MutableState<String> = mutableStateOf(emptyString())
    val isValueValid: MutableState<Boolean> = mutableStateOf(false)
    val valueError: MutableState<String> = mutableStateOf(emptyString())

    val description: MutableState<String> = mutableStateOf(emptyString())
    val isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    val descriptionError: MutableState<String> = mutableStateOf(emptyString())

    var isEnabledRegisterButton: MutableState<Boolean> = mutableStateOf(false)

    private val _year = MutableStateFlow("")
    private val _months = MutableStateFlow(false)

    fun addAccount() = viewModelScope.launch {
        val expense = Expense(name = name.value, description = description.value, created_at = DateUtils.getDate(), due_date = 10)
        val idExpense = addExpenseUseCase(expense)
        id.value = idExpense.toInt()
        for (month in listOf("1 - JANEIRO", "11 - NOVEMBRO", "12 - DEZEMBRO")){
            val monthlyPayment = MonthlyPayment(
                id_expense = idExpense.toInt(),
                year = "2023",
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

    private fun shouldEnabledRegisterButton() {
        isEnabledRegisterButton.value = nameError.value.isEmpty()
                && valueError.value.isEmpty()
                && descriptionError.value.isEmpty()
    }

    fun validateName() {
        if (name.value.length < 4) {
            isNameValid.value = true
            nameError.value = "O nome deve conter no mínimo 4 caracteres"
        } else {
            isNameValid.value = false
            nameError.value = emptyString()
        }
        shouldEnabledRegisterButton()
    }

    fun validateValue() {
        if (value.value.isEmpty()) {
            isValueValid.value = true
            valueError.value = "O valor é obrigatório"
        } else {
            isValueValid.value = false
            valueError.value = emptyString()
        }
        shouldEnabledRegisterButton()
    }

    fun validateDescription() {
        if (description.value.isEmpty()) {
            isDescriptionValid.value = true
            descriptionError.value = "a descrição é obrigatória"
        } else {
            isDescriptionValid.value = false
            descriptionError.value = emptyString()
        }
        shouldEnabledRegisterButton()
    }

    private fun isYearValid(year: String): Boolean {
        return year.isNotEmpty()
    }

    private fun isMonthsValid(months: List<String>): Boolean {
        return months.isNotEmpty()
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