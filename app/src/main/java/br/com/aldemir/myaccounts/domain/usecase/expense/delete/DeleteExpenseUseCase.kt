package br.com.aldemir.myaccounts.domain.usecase.expense.delete

import br.com.aldemir.myaccounts.data.model.Expense

interface DeleteExpenseUseCase {
    suspend operator fun invoke(expense: Expense): Int
}