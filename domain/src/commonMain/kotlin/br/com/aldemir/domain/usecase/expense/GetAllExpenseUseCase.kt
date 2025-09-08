package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.domain.model.ExpenseDomain

class GetAllExpenseUseCase constructor(
    private val expenseRepository: ExpenseRepository
): BaseUseCase<Unit, List<ExpenseDomain>> {

    override suspend fun execute(params: Unit): List<ExpenseDomain> {
        return expenseRepository.getAll()
    }
}