package br.com.aldemir.home.domain.usecase.expense.delete

import br.com.aldemir.publ.domain.expense.DeleteExpenseUseCase
import br.com.aldemir.data.repository.expense.ExpenseRepository
import br.com.aldemir.data.database.model.ExpenseDTO

class DeleteExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
): DeleteExpenseUseCase {
    override suspend fun invoke(expenseDTO: ExpenseDTO): Int {
        return expenseRepository.delete(expenseDTO)
    }
}