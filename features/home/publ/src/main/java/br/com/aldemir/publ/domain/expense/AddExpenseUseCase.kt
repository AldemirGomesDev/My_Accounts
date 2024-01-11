package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseDTO

interface AddExpenseUseCase {
    suspend operator fun invoke(expenseDTO: ExpenseDTO): Long
}