package br.com.aldemir.myaccounts.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.domain.mapper.toExpenseView
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.*
import br.com.aldemir.myaccounts.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.myaccounts.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.myaccounts.presentation.main.state.MainUiState
import br.com.aldemir.myaccounts.presentation.shared.model.ExpenseView
import br.com.aldemir.myaccounts.presentation.theme.HighPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.LowPriorityColor
import br.com.aldemir.myaccounts.presentation.theme.MediumPriorityColor
import br.com.aldemir.myaccounts.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase,
    private val saveDarkModeStateUseCase: SaveDarkModeStateUseCase,
    private val readDarkModeStateUseCase: ReadDarkModeStateUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(MainUiState())
    val uiState: State<MainUiState> = _uiState

    private val _expenses = MutableStateFlow<List<ExpenseView>>(emptyList())
    var expenses: StateFlow<List<ExpenseView>> = _expenses

    private val _monthExpenses = MutableStateFlow<List<MonthlyPayment>>(emptyList())

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

    fun setDarkMode(){
        val isDarkMode = !_uiState.value.darkMode
        saveDarkModeState(isDarkMode)
    }

    fun onOpenDialogClicked() {
        _showDialog.value = true
    }

    fun onDialogConfirm() {
        _showDialog.value = false
    }

    fun onDialogDismiss() {
        _showDialog.value = false
    }

    fun readDarkModeState() {
        try {
            viewModelScope.launch {
                val darkModeState = readDarkModeStateUseCase.invoke()
                darkModeState
                .collect {
                    _uiState.value = _uiState.value.copy(darkMode = it)
                }
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(darkMode = false)
        }
    }

    private fun saveDarkModeState(isDarkMode: Boolean) {
        viewModelScope.launch {
            saveDarkModeStateUseCase(isDarkMode)
            readDarkModeState()
        }
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
        val expenses = ArrayList<ExpenseView>()
        expensesPerMonth.forEach { expensePerMonth ->
            val expense = expensePerMonth.toExpenseView(
                checkIfExpired(
                    DateUtils.getDay(),
                    expensePerMonth.due_date
                )
            )
            expenses.add(expense)
        }
        _expenses.value = expenses.toList()
    }

    fun getMessageToast(message: String) {
        val contentString = ObservableInt()
        contentString.set(R.string.delete_expense_message_toast)
    }

    private fun checkIfExpired(currentDay: Int, dueDay: Int): Boolean {
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

    private fun calculateValues() {
        clearValues()
        for (item in _monthExpenses.value) {
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
}