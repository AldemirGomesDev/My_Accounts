package br.com.aldemir.expense.presentation.historic.model

import br.com.aldemir.common.util.emptyString

data class HistoricExpenseUiModel(
    val expensePerMonthUiModelList: List<ExpensePerMonthUiModel> = emptyList(),
    val monthOptionSelected: String = emptyString(),
    val isLoading: Boolean = false,
    val yearsList: List<String> = emptyList()
)
