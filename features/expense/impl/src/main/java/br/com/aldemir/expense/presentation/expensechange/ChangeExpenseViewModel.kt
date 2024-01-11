package br.com.aldemir.expense.presentation.expensechange

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.publ.domain.expense.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.publ.domain.expense.UpdateMonthlyPaymentUseCase
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.fromCurrency
import br.com.aldemir.common.util.pointString
import br.com.aldemir.common.util.zeroString
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangeExpenseViewModel constructor(
    private val updateMonthlyPaymentUseCase: br.com.aldemir.publ.domain.expense.UpdateMonthlyPaymentUseCase,
    private val getByIdMonthlyPaymentUseCase: br.com.aldemir.publ.domain.expense.GetByIdMonthlyPaymentUseCase
) : ViewModel() {

    val value: MutableState<String> = mutableStateOf(emptyString())

    private val _Expense_monthlyDTO = MutableStateFlow(ExpenseMonthlyDTO())
    var expenseMonthlyDTO: StateFlow<ExpenseMonthlyDTO> = _Expense_monthlyDTO

    private val _idMonthlyPayment = MutableStateFlow(0)
    val idMonthlyPayment = _idMonthlyPayment.asStateFlow()

    fun getAllByIdMonthlyPayment(id: Int) = viewModelScope.launch {
        _Expense_monthlyDTO.value = getByIdMonthlyPaymentUseCase(id)!!
    }

    fun updateMonthlyPayment() = viewModelScope.launch {
        _Expense_monthlyDTO.value.value = value.value.fromCurrency()
        _idMonthlyPayment.value = updateMonthlyPaymentUseCase(_Expense_monthlyDTO.value)!!
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