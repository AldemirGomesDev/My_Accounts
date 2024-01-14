package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.domain.model.ExpenseDomain

class DeleteExpenseUseCase constructor(
    private val expenseRepository: ExpenseRepository
): BaseUseCase<ExpenseDomain, Int> {

    override suspend fun execute(params: ExpenseDomain): Int {
        return expenseRepository.delete(params)
    }
}