package br.com.aldemir.myaccounts.domain.usecase.expense.delete

import br.com.aldemir.myaccounts.data.model.ExpenseDTO

interface DeleteExpenseUseCase {
    suspend operator fun invoke(expenseDTO: ExpenseDTO): Int
}