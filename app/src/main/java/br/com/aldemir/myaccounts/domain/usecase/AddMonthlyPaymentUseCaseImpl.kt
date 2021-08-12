package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import javax.inject.Inject

class AddMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): AddMonthlyPaymentUseCase {
    override suspend fun invoke(monthlyPayment: MonthlyPayment): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(monthlyPayment)
    }
}