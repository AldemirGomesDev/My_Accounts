package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseDTO

interface DeleteExpenseUseCase {
    suspend operator fun invoke(expenseDTO: ExpenseDTO): Int
}