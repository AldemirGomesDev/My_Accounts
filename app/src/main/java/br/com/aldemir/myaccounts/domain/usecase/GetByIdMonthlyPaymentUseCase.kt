package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface GetByIdMonthlyPaymentUseCase {
    suspend operator fun invoke(id: Int): MonthlyPayment
}