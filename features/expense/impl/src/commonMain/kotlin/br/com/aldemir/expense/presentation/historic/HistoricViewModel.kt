package br.com.aldemir.expense.presentation.historic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.theme.HighPriorityColor
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllMonthlyPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.account_pending
import myaccounts.common.generated.resources.expense_expired
import myaccounts.common.generated.resources.expense_paid_out
import org.jetbrains.compose.resources.StringResource

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

    fun checkIfExpired(dueDay: Int, month: String, year: String): Boolean {
        return (year == DateUtils.getYearString() && month == DateUtils.getMonthString() && DateUtils.getCurrentDay() > dueDay)
    }

    fun getStatusColor(status: Boolean, expired: Boolean): Color {
        return if (status) LowPriorityColor
        else if (expired) HighPriorityColor
        else MediumPriorityColor
    }

    fun getStatusText(status: Boolean, expired: Boolean): StringResource {
        return if (status) Res.string.expense_paid_out
        else if (expired) Res.string.expense_expired
        else Res.string.account_pending
    }
}