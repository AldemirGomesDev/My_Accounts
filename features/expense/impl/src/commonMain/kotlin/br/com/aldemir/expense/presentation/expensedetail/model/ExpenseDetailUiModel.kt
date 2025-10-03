package br.com.aldemir.expense.presentation.expensedetail.model

import br.com.aldemir.expense.model.MonthlyPaymentView

data class ExpenseDetailUiModel(
    val monthlyExpenses : List<MonthlyPaymentView> = emptyList(),
    val showDialog: Boolean = false,
    val idMonthlyExpense: Int = 0
)
