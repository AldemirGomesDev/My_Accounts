package br.com.aldemir.myaccounts.domain.usecase.expense.add

import br.com.aldemir.myaccounts.data.model.ExpenseDTO

interface AddExpenseUseCase {
    suspend operator fun invoke(expenseDTO: ExpenseDTO): Long
}