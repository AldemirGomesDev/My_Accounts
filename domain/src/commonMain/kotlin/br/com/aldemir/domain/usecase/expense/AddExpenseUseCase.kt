package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.ExpenseDomain
import br.com.aldemir.domain.repository.ExpenseRepository

class AddExpenseUseCase constructor(
    private val expenseRepository: ExpenseRepository
): BaseUseCase<ExpenseDomain, Long> {
    override suspend fun execute(params: ExpenseDomain): Long {
        return expenseRepository.insertExpense(params)
    }
}