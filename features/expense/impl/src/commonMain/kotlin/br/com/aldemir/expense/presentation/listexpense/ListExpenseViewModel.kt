package br.com.aldemir.expense.presentation.listexpense

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.usecase.expense.DeleteExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase.Params
import br.com.aldemir.expense.mapper.toDomain
import br.com.aldemir.expense.mapper.toExpenseView
import br.com.aldemir.expense.model.ExpenseView
import br.com.aldemir.expense.presentation.listexpense.action.ExpenseUiAction
import br.com.aldemir.expense.presentation.listexpense.mapper.toUiModel
import br.com.aldemir.expense.presentation.listexpense.model.ExpenseUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_paid_out
import org.jetbrains.compose.resources.StringResource

class ListExpenseViewModel(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getAllExpensesMonthUseCase: GetAllExpensesMonthUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiModel())
    var uiState = _uiState.asStateFlow()

    fun sendAction(action: ExpenseUiAction) {
        when (action) {
            is ExpenseUiAction.LoadData -> {
                getAllExpensesMonth()
            }
            is ExpenseUiAction.DeleteExpense -> {
                delete(action.expenseView)
            }
            is ExpenseUiAction.UpdateExpenseMonthly -> {
                getAllExpensePerMonth()
            }
            is ExpenseUiAction.ShowDialog -> {
                shouldShowDialog(action.show)
            }
        }

    }

    private fun shouldShowDialog(show: Boolean) {
        _uiState.update {
            it.copy(showDialog = show)
        }
    }

    private fun getAllExpensesMonth() = viewModelScope.launch {
        val params = Params(DateUtils.getMonthString(), DateUtils.getYearString())
        getAllExpensesMonthUseCase(this, params) {
            success = { listExpenses ->
                _uiState.update {
                    it.copy(
                        monthExpenses = listExpenses.map { item -> item.toUiModel() }
                    )
                }
                calculateValues()
            }
            error = {
                println(it.message)
                calculateValues()
            }
        }
    }

    private fun delete(expenseView: ExpenseView) = viewModelScope.launch {
        deleteExpenseUseCase(this, expenseView.toDomain()) {
            success = { expenseId ->
                if (expenseId > 0) {
                    getAllExpensesMonth()
                }
            }
        }
    }

    private fun getAllExpensePerMonth() = viewModelScope.launch {
        getAllExpensePerMonthUseCase(
            this, GetAllExpensePerMonthUseCase.Params(
                DateUtils.getMonthString(),
                DateUtils.getYearString()
            )
        ) {
            success = { listExpensePerMonth ->
                convertToExpenses(listExpensePerMonth)
            }
        }
    }

    private fun convertToExpenses(expensesPerMonth: List<ExpensePerMonthDomain>) {
        val currentDay = DateUtils.getCurrentDay()
        val expenses = expensesPerMonth.map { expensePerMonth ->
            expensePerMonth.toExpenseView(
                checkIfExpired(currentDay, expensePerMonth.due_date)
            )
        }
        _uiState.update {
            it.copy(expenses = expenses)
        }
    }

    private fun checkIfExpired(currentDay: Int, dueDay: Int): Boolean {
        return currentDay > dueDay
    }

    private fun calculateValues() {
        clearValues()
        val expenses = _uiState.value.monthExpenses
        val totalValue = expenses.sumOf { it.value }
        val paidOut = expenses.filter { it.situation }.sumOf { it.value }
        val pending = expenses.filter { !it.situation }.sumOf { it.value }

        _uiState.update {
            it.copy(
                totalValue = totalValue,
                paidOut = paidOut,
                pending = pending
            )
        }
        calculatePercentage()
    }

    private fun clearValues() {
        _uiState.update {
            it.copy(
                totalValue = 0.0,
                paidOut = 0.0,
                pending = 0.0,
                percentage = 0f
            )
        }
    }

    private fun calculatePercentage() {
        val percentage = ((_uiState.value.paidOut / _uiState.value.totalValue) * 100).toFloat()
        _uiState.update {
            it.copy(percentage = if (percentage.isNaN()) 0f else percentage)
        }
    }
}