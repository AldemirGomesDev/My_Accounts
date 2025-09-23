package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.repository.ExpenseRepository

class AddExpenseUseCase(
    private val expenseRepository: ExpenseRepository
): UseCase<ExpenseDomain, Long> {
    override suspend fun execute(params: ExpenseDomain): Long {
        return expenseRepository.insertExpense(params)
    }
}