package br.com.aldemir.myaccounts.domain.usecase.di

import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO

interface GetAllMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpenseMonthlyDTO>
}