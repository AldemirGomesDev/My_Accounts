package br.com.aldemir.home.domain.usecase.expense.getexpense

import br.com.aldemir.data.database.model.ExpenseDTO

interface GetAllExpenseUseCase {
    suspend operator fun invoke(): List<ExpenseDTO>
}