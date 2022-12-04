package br.com.aldemir.myaccounts.ui.expensechange

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.domain.usecase.UpdateMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeExpenseViewModel @Inject constructor(
    private val updateMonthlyPaymentUseCase: UpdateMonthlyPaymentUseCase,
    private val getByIdMonthlyPaymentUseCase: GetByIdMonthlyPaymentUseCase
) : ViewModel() {

    val value: MutableState<String> = mutableStateOf(emptyString())

    private val _monthlyPayment = MutableStateFlow(MonthlyPayment())
    var monthlyPayment: StateFlow<MonthlyPayment> = _monthlyPayment

    private val _idMonthlyPayment = MutableStateFlow(0)
    val idMonthlyPayment = _idMonthlyPayment.asStateFlow()

    fun getAllByIdMonthlyPayment(id: Int) = viewModelScope.launch {
        _monthlyPayment.value = getByIdMonthlyPaymentUseCase(id)!!
    }

    fun updateMonthlyPayment() = viewModelScope.launch {
        _monthlyPayment.value.value = value.value.fromCurrency()
        _idMonthlyPayment.value = updateMonthlyPaymentUseCase(_monthlyPayment.value)!!
    }
}