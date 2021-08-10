package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.domain.model.Expense

class GetAllExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
): GetAllExpenseUseCase {
    override suspend fun invoke(): List<Expense> {
        return expenseRepository.getAll()
    }
}