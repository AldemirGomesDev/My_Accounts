package br.com.aldemir.myaccounts.domain.usecase.expense.delete

import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.model.ExpenseDTO
import javax.inject.Inject

class DeleteExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): DeleteExpenseUseCase {
    override suspend fun invoke(expenseDTO: ExpenseDTO): Int {
        return expenseRepository.delete(expenseDTO)
    }
}