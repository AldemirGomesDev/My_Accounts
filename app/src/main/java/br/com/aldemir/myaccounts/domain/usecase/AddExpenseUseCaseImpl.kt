package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.domain.model.Expense

class AddExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
): AddExpenseUseCase {
    override suspend fun invoke(expense: Expense): Long {
        return expenseRepository.insertExpense(expense)
    }
}