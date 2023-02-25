package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.model.Expense
import javax.inject.Inject

class GetAllExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): GetAllExpenseUseCase {
    override suspend fun invoke(): List<Expense> {
        return expenseRepository.getAll()
    }
}