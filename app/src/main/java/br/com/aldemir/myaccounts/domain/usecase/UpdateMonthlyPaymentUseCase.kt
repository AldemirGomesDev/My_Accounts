package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

interface UpdateMonthlyPaymentUseCase {
    suspend operator fun invoke(monthlyPayment: MonthlyPayment): Int
}