package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain

interface GetAllExpensesMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpenseMonthlyDomain>
}