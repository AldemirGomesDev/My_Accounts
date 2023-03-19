package br.com.aldemir.myaccounts.domain.usecase.expense.getexpense

import br.com.aldemir.myaccounts.data.model.ExpenseDTO

interface GetAllExpenseUseCase {
    suspend operator fun invoke(): List<ExpenseDTO>
}