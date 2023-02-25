package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.Expense

interface DeleteExpenseUseCase {
    suspend operator fun invoke(expense: Expense): Int
}