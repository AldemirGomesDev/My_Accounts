package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.domain.model.ExpenseDomain

class DeleteExpenseUseCase(
    private val expenseRepository: ExpenseRepository
): UseCase<ExpenseDomain, Int> {

    override suspend fun execute(params: ExpenseDomain): Int {
        return expenseRepository.delete(params)
    }
}