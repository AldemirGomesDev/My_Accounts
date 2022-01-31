package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.DeleteExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpensePerMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpensesMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getAllExpenseUseCase: GetAllExpenseUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase
) : ViewModel() {
    private val _expenses = MutableLiveData<List<Expense>>()
    var expenses: LiveData<List<Expense>> = _expenses

    private val _monthExpenses = MutableLiveData<List<MonthlyPayment>>()
    val monthExpenses: LiveData<List<MonthlyPayment>> = _monthExpenses

    private val _monthStatus = MutableLiveData<List<Boolean>>()
    val monthStatus: LiveData<List<Boolean>> = _monthStatus

    private val _idExpense = MutableLiveData(0)
    val idExpense: LiveData<Int> = _idExpense

    fun getAllExpensesMonth(month: String, year: String) = viewModelScope.launch {
        _monthExpenses.value = getAllExpensesMonthUseCase(month, year)!!
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        _idExpense.value = deleteExpenseUseCase(expense)
    }
    fun setId(id: Int) {
      _idExpense.value = id
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        val expensePerMonth = getAllExpensePerMonthUseCase(month, year)!!
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

            status.add(item.situation)
            expenses.add(expense)

        }
        _monthStatus.value = status
        _expenses.value = expenses
    }
}