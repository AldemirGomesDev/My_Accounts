package br.com.aldemir.myaccounts.presentation.expense.expensechange

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.update.UpdateMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.fromCurrency
import br.com.aldemir.myaccounts.util.pointString
import br.com.aldemir.myaccounts.util.zeroString
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

    fun getValueWithTwoDecimal(value: String): String {
        val newValue = if (verifyTwoCharactersAfterPoint(value)) "$value${zeroString()}"
        else removePointString(value)
        return removePointString(newValue)
    }

    private fun removePointString(value: String): String {
        return value.replace(pointString(), emptyString())
    }

    private fun verifyTwoCharactersAfterPoint(value: String): Boolean {
        val string = value.split(pointString())
        return string[1].length == 1
    }
}