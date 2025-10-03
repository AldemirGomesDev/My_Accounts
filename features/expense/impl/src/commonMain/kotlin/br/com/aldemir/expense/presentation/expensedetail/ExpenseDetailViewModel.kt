package br.com.aldemir.expense.presentation.expensedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.domain.usecase.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.domain.usecase.expense.UpdateExpenseSituationUseCase
import br.com.aldemir.domain.usecase.expense.UpdateExpenseSituationUseCase.Params
import br.com.aldemir.expense.mapper.toView
import br.com.aldemir.expense.presentation.expensedetail.action.ExpenseDetailAction
import br.com.aldemir.expense.presentation.expensedetail.model.ExpenseDetailUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseDetailViewModel(
    private val updateExpenseSituationUseCase: UpdateExpenseSituationUseCase,
    private val getAllByIdExpenseUseCase: GetAllByIdExpenseUseCase
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ExpenseDetailUiModel())
    var uiModel = _uiModel.asStateFlow()

    private var idExpense: Int = 0

    fun handleAction(action: ExpenseDetailAction) {
        when (action) {
            is ExpenseDetailAction.FetchExpenseDetail -> {
                getAllByIdExpense(action.id)
            }

            is ExpenseDetailAction.UpdateExpenseSituation -> {
                updateMonthlyPayment()
            }

            is ExpenseDetailAction.ShowDialog -> {
                shouldShowDialog(action.show)
            }

            is ExpenseDetailAction.OnUpdateClicked -> {
                updateIdMonthlyExpense(action.id)
                shouldShowDialog(true)
            }
        }
    }

    private fun shouldShowDialog(show: Boolean) {
        _uiModel.update {
            it.copy(showDialog = show)
        }
    }

    private fun getAllByIdExpense(id: Int) = viewModelScope.launch {
        idExpense = id
        getAllByIdExpenseUseCase(this, id) {
            success = { monthlyPaymentDomain ->
                val monthlyPaymentViewList = monthlyPaymentDomain.map { item ->
                    item.toView()
                }
                _uiModel.update { current ->
                    current.copy(monthlyExpenses = monthlyPaymentViewList)
                }
            }
        }
    }

    private fun updateMonthlyPayment() = viewModelScope.launch {
        val idMonthlyExpense = _uiModel.value.idMonthlyExpense
        updateExpenseSituationUseCase(this, Params(idMonthlyExpense, true)) {
            success = { id ->
                if (id > 0) getAllByIdExpense(idExpense)
            }
            error = { exception ->
                exception.printStackTrace()
            }
        }
    }

    private fun updateIdMonthlyExpense(id: Int) {
        _uiModel.update {
            it.copy(idMonthlyExpense = id)
        }
    }
}