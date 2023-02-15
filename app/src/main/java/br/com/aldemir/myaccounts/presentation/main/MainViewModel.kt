package br.com.aldemir.myaccounts.presentation.main

import androidx.compose.ui.graphics.Color
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.*
import br.com.aldemir.myaccounts.presentation.theme.HighPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.LowPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.MediumPriorityColor
import br.com.aldemir.myaccounts.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    private var _valueTotal = MutableStateFlow(0.0)
    val valueTotal: StateFlow<Double> = _valueTotal.asStateFlow()

    private var _paidOut = MutableStateFlow(0.0)
    val paidOut: StateFlow<Double> = _paidOut.asStateFlow()

    private var _pending = MutableStateFlow(0.0)
    val pending: StateFlow<Double> = _pending.asStateFlow()

    private var _percentage = MutableStateFlow(0F)
    val percentage: StateFlow<Float> = _percentage.asStateFlow()

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    private fun calculateValues() {
        clearValues()
        for (item in monthExpenses.value) {
            _valueTotal.value += item.value
            if (item.situation) {
                _paidOut.value += item.value
            } else {
                _pending.value += item.value
            }
        }
        calculatePercentage()
    }

    private fun clearValues() {
        _valueTotal.value = 0.0
        _paidOut.value = 0.0
        _pending.value = 0.0
    }

    private fun calculatePercentage() {
        _percentage.value = ((paidOut.value / valueTotal.value) * 100).toFloat()
    }

    fun getAllExpensesMonth(month: String, year: String) = viewModelScope.launch {
        _monthExpenses.value = getAllExpensesMonthUseCase(month, year)
        calculateValues()
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        val expenseId = deleteExpenseUseCase(expense)
        if (expenseId > 0) {
            getAllExpensesMonth(DateUtils.getMonth(), DateUtils.getYear())
        }
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

    fun getMessageToast(message: String) {
        val contentString = ObservableInt()
        contentString.set(R.string.delete_expense_message_toast)
    }

    fun checkIfExpired(currentDay: Int, dueDay: Int): Boolean {
        return currentDay > dueDay
    }

    fun getStatusColor(status: Boolean, expired: Boolean): Color {
        return if (status) LowPriorityColor
        else if (expired) HighPriorityColor
        else MediumPriorityColor
    }

    fun getStatusText(status: Boolean, expired: Boolean): Int {
        return if (status) R.string.expense_paid_out
        else if (expired) R.string.expense_expired
        else R.string.expense_pending
    }
}