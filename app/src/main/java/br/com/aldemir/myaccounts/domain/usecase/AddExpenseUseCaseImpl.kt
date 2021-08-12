package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.domain.model.Expense
import javax.inject.Inject

class AddExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): AddExpenseUseCase {
    override suspend fun invoke(expense: Expense): Long {
        return expenseRepository.insertExpense(expense)
    }
}