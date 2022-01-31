package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

interface GetAllMonthlyPaymentUseCase {
    suspend operator fun invoke(): List<MonthlyPayment>
}