package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.model.Expense
import javax.inject.Inject

class AddExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): AddExpenseUseCase {
    override suspend fun invoke(expense: Expense): Long {
        return expenseRepository.insertExpense(expense)
    }
}