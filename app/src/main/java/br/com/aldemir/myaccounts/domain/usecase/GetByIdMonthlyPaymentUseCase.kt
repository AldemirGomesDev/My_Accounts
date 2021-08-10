package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

interface GetByIdMonthlyPaymentUseCase {
    suspend operator fun invoke(id: Int): MonthlyPayment
}