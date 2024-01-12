package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.domain.model.ExpenseDomain

class GetAllExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
): GetAllExpenseUseCase {
    override suspend fun invoke(): List<ExpenseDomain> {
        return expenseRepository.getAll()
    }
}