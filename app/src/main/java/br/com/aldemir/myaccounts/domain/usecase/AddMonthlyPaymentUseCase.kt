package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface AddMonthlyPaymentUseCase {
    suspend operator fun invoke(monthlyPayment: MonthlyPayment): Long
}