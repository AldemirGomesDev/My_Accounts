package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

class UpdateMonthlyPaymentUseCaseImpl(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UpdateMonthlyPaymentUseCase {
    override suspend fun invoke(monthlyPayment: MonthlyPayment): Int {
        return monthlyPaymentRepository.update(monthlyPayment)
    }
}