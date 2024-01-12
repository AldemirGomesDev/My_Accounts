package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseDomain

interface GetAllExpenseUseCase {
    suspend operator fun invoke(): List<ExpenseDomain>
}