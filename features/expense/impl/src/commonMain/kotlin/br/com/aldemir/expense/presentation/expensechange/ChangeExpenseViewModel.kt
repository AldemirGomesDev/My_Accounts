package br.com.aldemir.expense.presentation.expensechange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.util.fromCurrency
import br.com.aldemir.domain.usecase.expense.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.UpdateExpenseValueUseCase
import br.com.aldemir.expense.presentation.expensechange.action.ChangeExpenseAction
import br.com.aldemir.expense.presentation.expensechange.effect.ChangeExpenseEffect
import br.com.aldemir.expense.presentation.expensechange.model.ChangeExpenseUiModel
import br.com.aldemir.expense.presentation.listexpense.mapper.toChangeExpenseUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangeExpenseViewModel(
    private val updateExpenseValueUseCase: UpdateExpenseValueUseCase,
    private val getByIdMonthlyPaymentUseCase: GetByIdMonthlyPaymentUseCase
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ChangeExpenseUiModel())
    var uiModel = _uiModel.asStateFlow()

    private val _uiEffect = Channel<ChangeExpenseEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun sendAction(action: ChangeExpenseAction) {
        when(action) {
            is ChangeExpenseAction.LoadExpense -> {
                getAllByIdMonthlyPayment(action.id)
            }
            is ChangeExpenseAction.UpdateMonthlyExpense -> {
                updateMonthlyPayment(action.id)
            }
            is ChangeExpenseAction.OnValueChange -> {
                handleValueChange(action.value)
            }
        }
    }

    private fun getAllByIdMonthlyPayment(id: Int) = viewModelScope.launch {
        getByIdMonthlyPaymentUseCase(this, id) {
            success = { expenseMonthly ->
                _uiModel.update {
                    expenseMonthly.toChangeExpenseUiModel()
                }
            }
        }
    }

    private fun sendEffect(effect: ChangeExpenseEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun updateMonthlyPayment(id: Int) = viewModelScope.launch {
        updateLoading(true)
        updateExpenseValueUseCase(this, UpdateExpenseValueUseCase.Params(
            id = id,
            value = _uiModel.value.value
        )) {
            success = {
                updateLoading(false)
                sendEffect(ChangeExpenseEffect.NavigateBack)
                _uiModel.update { it }
            }
            error = {
                updateLoading(false)
                println(it)
            }
        }
    }

    private fun handleValueChange(value: String) {
        _uiModel.update {
            it.copy(
                value = value.fromCurrency()
            )
        }
    }

    private fun updateLoading(loading: Boolean) {
        _uiModel.update {
            it.copy(
                loading = loading
            )
        }
    }
}