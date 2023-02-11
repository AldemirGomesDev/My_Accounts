package br.com.aldemir.myaccounts.presentation.historic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpensePerMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpensesMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllMonthlyPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricViewModel @Inject constructor (
    private val getAllMonthlyPaymentUseCase: GetAllMonthlyPaymentUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase
    ) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)

    private val _monthlyPayment = MutableLiveData<List<MonthlyPayment>>()
    var monthlyPayment: LiveData<List<MonthlyPayment>> = _monthlyPayment

    private val _monthExpenses = MutableLiveData<List<MonthlyPayment>>()
    val monthExpenses: LiveData<List<MonthlyPayment>> = _monthExpenses

    private val _expensePerMonth = MutableLiveData<List<ExpensePerMonth>>()
    val expensePerMonth: LiveData<List<ExpensePerMonth>> = _expensePerMonth

    private val _yearsList = MutableLiveData<List<String>>()
    val yearsList: LiveData<List<String>> = _yearsList

    fun getAllMonthlyPayment() = viewModelScope.launch {
        val result = getAllMonthlyPaymentUseCase()
        _monthlyPayment.value = result
        getDistinctYears(monthList = result)
    }

    fun getAllExpensesMonth(month: String, year: String) = viewModelScope.launch {
        val result = getAllExpensesMonthUseCase(month, year)
        _monthExpenses.value = result
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        isLoading.value = true
        val result = getAllExpensePerMonthUseCase(month, year)
        _expensePerMonth.value = result
        isLoading.value = false
    }

    private fun getDistinctYears(monthList: List<MonthlyPayment>) {
        val myYears = mutableListOf<String>()
        val yearUnique = monthList.distinctBy { it.year }
        yearUnique.forEach { myYears.add(it.year) }
        _yearsList.value = myYears
    }
}