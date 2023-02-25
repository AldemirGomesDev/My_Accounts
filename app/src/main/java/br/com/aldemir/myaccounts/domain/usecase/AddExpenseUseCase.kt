package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.Expense

interface AddExpenseUseCase {
    suspend operator fun invoke(expense: Expense): Long
}