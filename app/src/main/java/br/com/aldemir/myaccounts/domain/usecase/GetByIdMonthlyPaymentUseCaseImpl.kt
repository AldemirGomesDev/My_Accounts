package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

class GetByIdMonthlyPaymentUseCaseImpl(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetByIdMonthlyPaymentUseCase {
    override suspend fun invoke(id: Int): MonthlyPayment {
        return monthlyPaymentRepository.getByIdMonthlyPayment(id)
    }
}