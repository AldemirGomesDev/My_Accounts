package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth


interface GetAllExpensePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpensePerMonth>
}