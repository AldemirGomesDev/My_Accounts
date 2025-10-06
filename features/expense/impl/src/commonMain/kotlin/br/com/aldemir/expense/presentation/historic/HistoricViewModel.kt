package br.com.aldemir.expense.presentation.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllMonthlyPaymentUseCase
import br.com.aldemir.expense.presentation.historic.action.HistoricExpenseAction
import br.com.aldemir.expense.presentation.historic.mapper.toListView
import br.com.aldemir.expense.presentation.historic.model.HistoricExpenseUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoricViewModel(
    private val getAllMonthlyPaymentUseCase: GetAllMonthlyPaymentUseCase,
    private val getAllExpensePerMonthUseCase: GetAllExpensePerMonthUseCase
) : ViewModel() {
    private val _uiModel = MutableStateFlow(HistoricExpenseUiModel())
    val uiModel = _uiModel.asStateFlow()

    fun handleEvent(event: HistoricExpenseAction) {
        when (event) {
            is HistoricExpenseAction.GetAllExpensePerMonth -> {
                getAllExpensePerMonth(_uiModel.value.monthOptionSelected,event.year)
            }

            is HistoricExpenseAction.FetchData -> {
                getAllMonthlyPayment()
            }

            is HistoricExpenseAction.UpdateMonthSelected -> {
                updateMonthSelected(event.month)
            }
        }
    }

    private fun getAllMonthlyPayment() = viewModelScope.launch {
        getAllMonthlyPaymentUseCase(this, Unit) {
            success = {
                getDistinctYears(monthList = it)
                getAllExpensePerMonth(DateUtils.getMonthString(), DateUtils.getYearString())
            }
        }
    }

    private fun getAllExpensePerMonth(month: String, year: String) = viewModelScope.launch {
        updateLoading(true)
        getAllExpensePerMonthUseCase(
            this, GetAllExpensePerMonthUseCase.Params(month, year)
        ) {
            success = { listExpensePerMonth ->
                _uiModel.update { expensePerMonth ->
                    expensePerMonth.copy(
                        expensePerMonthUiModelList = listExpensePerMonth.toListView(),
                        isLoading = false
                    )
                }
            }
            error = {
                updateLoading(false)
            }
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        _uiModel.update { currentState ->
            currentState.copy(
                isLoading = isLoading
            )
        }
    }

    private fun updateMonthSelected(month: String) {
        _uiModel.update { currentState ->
            currentState.copy(
                monthOptionSelected = month
            )
        }
    }

    private fun getDistinctYears(monthList: List<ExpenseMonthlyDomain>) {
        val years = monthList.map { it.year }.distinct()
        _uiModel.update { currentState ->
            currentState.copy(yearsList = years)
        }
    }
}