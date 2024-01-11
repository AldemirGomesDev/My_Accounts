package br.com.aldemir.home.domain.usecase.expense.add

import br.com.aldemir.publ.domain.expense.AddExpenseUseCase
import br.com.aldemir.data.repository.expense.ExpenseRepository

class AddExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
): AddExpenseUseCase {
    override suspend fun invoke(expenseDTO: br.com.aldemir.data.database.model.ExpenseDTO): Long {
        return expenseRepository.insertExpense(expenseDTO)
    }
}