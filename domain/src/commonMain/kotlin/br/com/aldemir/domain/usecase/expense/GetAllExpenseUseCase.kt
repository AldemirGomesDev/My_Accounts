package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.domain.model.ExpenseDomain

class GetAllExpenseUseCase(
    private val expenseRepository: ExpenseRepository
): UseCase<Unit, List<ExpenseDomain>> {

    override suspend fun execute(params: Unit): List<ExpenseDomain> {
        return expenseRepository.getAll()
    }
}