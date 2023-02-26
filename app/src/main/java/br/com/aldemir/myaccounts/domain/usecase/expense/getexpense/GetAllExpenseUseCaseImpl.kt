package br.com.aldemir.myaccounts.domain.usecase.expense.getexpense

import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.model.Expense
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpense.GetAllExpenseUseCase
import javax.inject.Inject

class GetAllExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): GetAllExpenseUseCase {
    override suspend fun invoke(): List<Expense> {
        return expenseRepository.getAll()
    }
}