package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.ExpensePerMonth


interface GetAllExpensePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpensePerMonth>
}