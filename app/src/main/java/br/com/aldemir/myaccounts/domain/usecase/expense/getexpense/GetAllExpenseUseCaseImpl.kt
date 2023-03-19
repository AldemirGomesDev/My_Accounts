package br.com.aldemir.myaccounts.domain.usecase.expense.getexpense

import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.model.ExpenseDTO
import javax.inject.Inject

class GetAllExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): GetAllExpenseUseCase {
    override suspend fun invoke(): List<ExpenseDTO> {
        return expenseRepository.getAll()
    }
}