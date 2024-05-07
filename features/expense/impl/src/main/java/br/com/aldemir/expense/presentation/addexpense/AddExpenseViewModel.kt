package br.com.aldemir.expense.presentation.addexpense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.util.Const.TAG
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.fromCurrency
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.usecase.expense.AddExpenseUseCase
import br.com.aldemir.domain.usecase.expense.AddMonthlyPaymentUseCase
import br.com.aldemir.expense.presentation.addexpense.action.AddExpenseAction
import br.com.aldemir.expense.presentation.addexpense.state.AddExpenseUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val addMonthlyPaymentUseCase: AddMonthlyPaymentUseCase
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<AddExpensesUiEffect>(replay = 0)
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddExpenseAction) {
        when (action) {
            AddExpenseAction.Submit -> saveAccount()
            AddExpenseAction.ClearForm -> clearForm()
            is AddExpenseAction.NameChanged -> handleNameChange(action.name)
            is AddExpenseAction.ValueChanged -> handleValueChange(action.value)
            is AddExpenseAction.DescriptionChanged -> handleDescriptionChange(action.description)
            is AddExpenseAction.AccountRepeatChanged -> handleAccountRepeat(action.checked)
            is AddExpenseAction.AmountThatRepeatsSelectedChanged -> handleAmountThatRepeatsSelected(action.amountThatRepeatsSelected)
            is AddExpenseAction.CheckedPaidChanged -> handleCheckedPaid(action.checked)
            is AddExpenseAction.DueDateSelectedChanged -> handleDueDateSelected(action.dueDate)
        }
    }

    private fun clearForm() {
        _uiState.update {
            it.copy(
                name = emptyString(),
                value = emptyString(),
                description = emptyString(),
                isCheckedPaid = false,
                isAccountRepeat = false,
                dueDateSelected = 0,
                amountThatRepeatsSelected = 0
            )
        }
        shouldEnabledRegisterButton()
    }

    private fun handleNameChange(name: String) {
        _uiState.update {
            it.copy(
                name = name
            )
        }
        validateName()
    }

    private fun handleValueChange(value: String) {
        _uiState.update {
            it.copy(
                value = value
            )
        }
        validateValue()
    }

    private fun handleDescriptionChange(description: String) {
        _uiState.update {
            it.copy(
                description = description
            )
        }
        validateDescription()
    }

    private fun handleAccountRepeat(checked: Boolean) {
        _uiState.update {
            it.copy(
                isAccountRepeat = checked
            )
        }
    }

    private fun handleCheckedPaid(checked: Boolean) {
        _uiState.update {
            it.copy(
                isCheckedPaid = checked
            )
        }
    }

    private fun handleDueDateSelected(dueDate: String) {
        _uiState.update {
            it.copy(
                dueDateSelected = dueDate.toInt()
            )
        }
    }

    private fun handleAmountThatRepeatsSelected(amountThatRepeatsSelected: String) {
        _uiState.update {
            it.copy(
                amountThatRepeatsSelected = amountThatRepeatsSelected.toInt()
            )
        }
    }

    private fun saveAccount() = viewModelScope.launch {
        val expenseDomain = ExpenseDomain(
            name = uiState.value.name,
            description = uiState.value.description,
            created_at = DateUtils.getDate(),
            due_date = uiState.value.dueDateSelected
        )
        addExpenseUseCase(this, expenseDomain).apply {
            onSuccess {
                handleMonthlyPayment(it)
            }
            onFailure {
                _uiEffect.emit(AddExpensesUiEffect.ShowError())
            }
        }

    }

    private fun handleMonthlyPayment(idExpense: Long) {
        val years = DateUtils.getYears(uiState.value.amountThatRepeatsSelected)
        val months = DateUtils.getMonths(uiState.value.amountThatRepeatsSelected)

        for ((index, month) in months.withIndex()) {
            val expenseMonthlyDomain = ExpenseMonthlyDomain(
                id_expense = idExpense.toInt(),
                year = years[index],
                month = month,
                value = uiState.value.value.fromCurrency(),
                situation = if (index == 0) uiState.value.isCheckedPaid else false
            )
            insertMonthlyPayment(expenseMonthlyDomain)
        }
    }

    private fun insertMonthlyPayment(expenseMonthlyDomain: ExpenseMonthlyDomain) =
        viewModelScope.launch {
            addMonthlyPaymentUseCase(this, expenseMonthlyDomain).apply {
                onSuccess {
                    _uiEffect.emit(AddExpensesUiEffect.ShowSuccess())
                }
                onFailure {
                    _uiEffect.emit(AddExpensesUiEffect.ShowError())
                }
            }
        }

    private fun shouldEnabledRegisterButton() {
        uiState.value.isEnabledRegisterButton = !validateLength(uiState.value.name, 3)
                && uiState.value.value.isNotEmpty()
                && !validateLength(uiState.value.description, 2)
    }

    private fun validateLength(text: String, minLength: Int) = text.length < minLength

    fun clearRepeatAmount(isChecked: Boolean) {
        val currentUiState = checkNotNull(uiState.value)
        if (!isChecked) _uiState.value = currentUiState.copy(amountThatRepeatsSelected = 1)
    }

    private fun validateName() {
        val currentUiState = checkNotNull(uiState.value)
        if (validateLength(uiState.value.name, 3)) {
            _uiState.value = currentUiState.copy(
                isNameValid = true,
                nameError = "O nome deve conter no mínimo 3 dígitos"
            )
        } else {
            _uiState.value = currentUiState.copy(
                isNameValid = false,
                nameError = emptyString()
            )
        }
        shouldEnabledRegisterButton()
    }

    private fun validateValue() {
        val currentUiState = checkNotNull(uiState.value)
        if (uiState.value.value.isEmpty()) {
            _uiState.value = currentUiState.copy(
                isValueValid = true,
                valueError = "O valor é obrigatório"
            )
        } else {
            _uiState.value = currentUiState.copy(
                isValueValid = false,
                valueError = emptyString()
            )
        }
        shouldEnabledRegisterButton()
    }

    private fun validateDescription() {
        val currentUiState = checkNotNull(uiState.value)
        if (validateLength(uiState.value.description, 2)) {
            _uiState.value = currentUiState.copy(
                isDescriptionValid = true,
                descriptionError = "A descrição deve conter no mínimo 2 dígitos"
            )
        } else {
            _uiState.value = currentUiState.copy(
                isDescriptionValid = false,
                descriptionError = emptyString()
            )
        }
        shouldEnabledRegisterButton()
    }

    fun getNumberOfTimesItRepeats(): MutableList<String> {
        Log.w(TAG, "getNumberOfTimesItRepeats: ")

        val numberOfTimesItRepeats = arrayListOf<String>()
        for (i in 1..100) {
            numberOfTimesItRepeats.add(i.toString())
        }
        return numberOfTimesItRepeats
    }

}