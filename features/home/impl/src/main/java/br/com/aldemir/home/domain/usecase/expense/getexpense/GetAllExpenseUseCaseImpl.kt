package br.com.aldemir.home.domain.usecase.expense.getexpense

import br.com.aldemir.data.repository.expense.ExpenseRepository
import br.com.aldemir.data.database.model.ExpenseDTO

class GetAllExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
): GetAllExpenseUseCase {
    override suspend fun invoke(): List<ExpenseDTO> {
        return expenseRepository.getAll()
    }
}