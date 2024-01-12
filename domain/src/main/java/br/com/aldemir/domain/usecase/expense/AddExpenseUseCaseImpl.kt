package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.repository.ExpenseRepository

class AddExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
): AddExpenseUseCase {
    override suspend fun invoke(expenseDomain: ExpenseDomain): Long {
        return expenseRepository.insertExpense(expenseDomain)
    }
}