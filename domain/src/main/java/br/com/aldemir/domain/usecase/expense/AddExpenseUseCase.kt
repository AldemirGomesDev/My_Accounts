package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseDomain

interface AddExpenseUseCase {
    suspend operator fun invoke(expenseDomain: ExpenseDomain): Long
}