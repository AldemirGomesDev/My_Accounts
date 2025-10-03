package br.com.aldemir.expense.presentation.historic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllMonthlyPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoricViewModel(
    private val getAllMonthlyPaymentUseCase: GetAllMonthlyPaymentUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)

    private val _expensePerMonthDomain = MutableStateFlow<List<ExpensePerMonthDomain>>(listOf())
    val expensePerMonthDomain = _expensePerMonthDomain.asStateFlow()

    private val _yearsList = MutableStateFlow<List<String>>(listOf())
    val yearsList = _yearsList.asStateFlow()

    fun getAllMonthlyPayment() = viewModelScope.launch {
        getAllMonthlyPaymentUseCase(this, Unit) {
            success = { getDistinctYears(monthList = it) }
        }
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        isLoading.value = true

        getAllExpensePerMonthUseCase(
            this, GetAllExpensePerMonthUseCase.Params(
                month, year
            )
        ) {
            success = { listExpensePerMonth ->
                _expensePerMonthDomain.update { listExpensePerMonth }
                isLoading.value = false
            }
            error = { isLoading.value = false }
        }
    }

    private fun getDistinctYears(monthList: List<ExpenseMonthlyDomain>) {
        val myYears = mutableListOf<String>()
        val yearUnique = monthList.distinctBy { it.year }
        yearUnique.forEach { myYears.add(it.year) }
        _yearsList.update { myYears }
    }
}