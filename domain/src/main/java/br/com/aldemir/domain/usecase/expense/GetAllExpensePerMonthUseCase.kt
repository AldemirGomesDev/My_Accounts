package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpensePerMonthDomain


interface GetAllExpensePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpensePerMonthDomain>
}