package br.com.aldemir.myaccounts.presentation.historic

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

    private val _monthlyPayment = MutableLiveData<List<MonthlyPayment>>()
    var monthlyPayment: LiveData<List<MonthlyPayment>> = _monthlyPayment

    private val _monthExpenses = MutableLiveData<List<MonthlyPayment>>()
    val monthExpenses: LiveData<List<MonthlyPayment>> = _monthExpenses

    private val _expensePerMonth = MutableLiveData<List<ExpensePerMonth>>()
    val expensePerMonth: LiveData<List<ExpensePerMonth>> = _expensePerMonth

    fun getAllMonthlyPayment() = viewModelScope.launch {
        _monthlyPayment.value = getAllMonthlyPaymentUseCase()!!
    }

    fun getAllExpensesMonth(month: String, year: String) = viewModelScope.launch {
        _monthExpenses.value = getAllExpensesMonthUseCase(month, year)!!
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        _expensePerMonth.value = getAllExpensePerMonthUseCase(month, year)!!
    }
}