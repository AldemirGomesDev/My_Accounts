package br.com.aldemir.myaccounts.domain.usecase.expense.add

import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.model.ExpenseDTO
import javax.inject.Inject

class AddExpenseUseCaseImpl @Inject constructor(
    private val expenseRepository: ExpenseRepository
): AddExpenseUseCase {
    override suspend fun invoke(expenseDTO: ExpenseDTO): Long {
        return expenseRepository.insertExpense(expenseDTO)
    }
}