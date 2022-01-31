package br.com.aldemir.myaccounts.domain.usecase.di

import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

interface GetAllMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<MonthlyPayment>
}