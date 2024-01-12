package br.com.aldemir.expense.presentation.listexpense

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.usecase.expense.DeleteExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.expense.mapper.toDomain
import br.com.aldemir.expense.mapper.toExpenseView
import br.com.aldemir.expense.model.ExpenseView
import br.com.aldemir.expense.presentation.listexpense.state.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListExpenseViewModel constructor(
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

    private val _monthExpenses = MutableStateFlow<List<ExpenseMonthlyDomain>>(emptyList())

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

    fun delete(expenseView: ExpenseView) = viewModelScope.launch {
        val expenseId = deleteExpenseUseCase(expenseView.toDomain())
        if (expenseId > 0) {
            getAllExpensesMonth(DateUtils.getMonth(), DateUtils.getYear())
        }
    }

    fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        val expensePerMonth = getAllExpensePerMonthUseCase(month, year)
        convertToExpenses(expensePerMonth)
    }

    private fun convertToExpenses(expensesPerMonth: List<ExpensePerMonthDomain>) {
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
        contentString.set(R.string.expense_delete_message_toast)
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
        else R.string.account_pending
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