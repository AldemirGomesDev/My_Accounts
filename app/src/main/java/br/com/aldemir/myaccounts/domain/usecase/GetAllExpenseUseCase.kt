package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.Expense

interface GetAllExpenseUseCase {
    suspend operator fun invoke(): List<Expense>
}