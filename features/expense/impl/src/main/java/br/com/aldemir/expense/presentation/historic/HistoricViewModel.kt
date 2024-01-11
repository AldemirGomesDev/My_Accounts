package br.com.aldemir.expense.presentation.historic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.publ.domain.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.publ.domain.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.publ.domain.expense.GetAllMonthlyPaymentUseCase
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.ExpensePerMonthDTO
import kotlinx.coroutines.launch

class HistoricViewModel constructor(
    private val getAllMonthlyPaymentUseCase: br.com.aldemir.publ.domain.expense.GetAllMonthlyPaymentUseCase,
    private val getAllExpensesMonthUseCase: br.com.aldemir.publ.domain.expense.GetAllExpensesMonthUseCase,
    private val getAllExpensePerMonthUseCase: br.com.aldemir.publ.domain.expense.GetAllExpensePerMonthUseCase
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)

    private val _Expense_monthlyDTO = MutableLiveData<List<ExpenseMonthlyDTO>>()
    var expenseMonthlyDTO: LiveData<List<ExpenseMonthlyDTO>> = _Expense_monthlyDTO

    private val _monthExpenses = MutableLiveData<List<ExpenseMonthlyDTO>>()
    val monthExpenses: LiveData<List<ExpenseMonthlyDTO>> = _monthExpenses

    private val _expensePerMonthDTO = MutableLiveData<List<ExpensePerMonthDTO>>()
    val expensePerMonthDTO: LiveData<List<ExpensePerMonthDTO>> = _expensePerMonthDTO

    private val _yearsList = MutableLiveData<List<String>>()
    val yearsList: LiveData<List<String>> = _yearsList

    fun getAllMonthlyPayment() = viewModelScope.launch {
        val result = getAllMonthlyPaymentUseCase()
        _Expense_monthlyDTO.value = result
        getDistinctYears(monthList = result)
    }

    fun getAllExpensesMonth(month: String, year: String) = viewModelScope.launch {
        val result = getAllExpensesMonthUseCase(month, year)
        _monthExpenses.value = result
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        isLoading.value = true
        val result = getAllExpensePerMonthUseCase(month, year)
        _expensePerMonthDTO.value = result
        isLoading.value = false
    }

    private fun getDistinctYears(monthList: List<ExpenseMonthlyDTO>) {
        val myYears = mutableListOf<String>()
        val yearUnique = monthList.distinctBy { it.year }
        yearUnique.forEach { myYears.add(it.year) }
        _yearsList.value = myYears
    }

    fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
        return (year == DateUtils.getYear() && month == DateUtils.getMonth() && DateUtils.getDay() > dueDay)
    }

    fun getStatusColor(status: Boolean, expired: Boolean): Color {
        return if (status) LowPriorityColor
        else if (expired) HighPriorityColor
        else MediumPriorityColor
    }

    fun getStatusText(status: Boolean, expired: Boolean): Int {
        return if (status) R.string.expense_paid_out
        else if (expired) R.string.expense_expired
        else R.string.account_pending
    }
}