package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface GetAllMonthlyPaymentUseCase {
    suspend operator fun invoke(): List<MonthlyPayment>
}