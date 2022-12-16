package br.com.aldemir.myaccounts.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.*
import br.com.aldemir.myaccounts.util.Const.TAG
import br.com.aldemir.myaccounts.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getAllExpenseUseCase: GetAllExpenseUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase,
    private val getAllMonthlyPaymentUseCase: GetAllMonthlyPaymentUseCase
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    var expenses: StateFlow<List<Expense>> = _expenses

    private val _monthExpenses = MutableStateFlow<List<MonthlyPayment>>(emptyList())
    val monthExpenses: StateFlow<List<MonthlyPayment>> = _monthExpenses

    private val _idExpense = MutableStateFlow(0)
    val idExpense: StateFlow<Int> = _idExpense

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun getAllExpensesMonth(month: String, year: String) = viewModelScope.launch {
        _monthExpenses.value = getAllExpensesMonthUseCase(month, year)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        _idExpense.value = deleteExpenseUseCase(expense)
        if (_idExpense.value > 0) {
            getAllExpensesMonth(DateUtils.getMonth(), DateUtils.getYear())
            setId(0)
        }
    }
    fun setId(id: Int) {
      _idExpense.value = id
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        val expensePerMonth = getAllExpensePerMonthUseCase(month, year)
        convertToExpenses(expensePerMonth)
    }

    private fun convertToExpenses(expensesPerMonth: List<ExpensePerMonth>) {
        var expense: Expense
        val expenses = ArrayList<Expense>()
        val status = ArrayList<Boolean>()

        for (item in expensesPerMonth) {
            expense = Expense()
            expense.id = item.id_expense
            expense.name = item.name
            expense.description = item.description
            expense.due_date = item.due_date
            expense.status = item.situation

            status.add(item.situation)
            expenses.add(expense)

        }
        _expenses.value = expenses.toList()
    }
}