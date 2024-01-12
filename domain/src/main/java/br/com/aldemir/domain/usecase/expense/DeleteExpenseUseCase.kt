package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseDomain

interface DeleteExpenseUseCase {
    suspend operator fun invoke(expenseDomain: ExpenseDomain): Int
}