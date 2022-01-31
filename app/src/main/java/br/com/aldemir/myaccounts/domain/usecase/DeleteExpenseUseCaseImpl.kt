package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.domain.model.Expense
import javax.inject.Inject

class DeleteExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): DeleteExpenseUseCase {
    override suspend fun invoke(expense: Expense): Int {
        return expenseRepository.delete(expense)
    }
}