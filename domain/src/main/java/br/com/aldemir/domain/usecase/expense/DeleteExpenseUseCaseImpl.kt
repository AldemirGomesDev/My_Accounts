package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.domain.model.ExpenseDomain

class DeleteExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
): DeleteExpenseUseCase {
    override suspend fun invoke(expenseDomain: ExpenseDomain): Int {
        return expenseRepository.delete(expenseDomain)
    }
}