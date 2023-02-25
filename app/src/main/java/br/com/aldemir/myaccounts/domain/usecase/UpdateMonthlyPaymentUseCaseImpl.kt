package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import javax.inject.Inject

class UpdateMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UpdateMonthlyPaymentUseCase {
    override suspend fun invoke(monthlyPayment: MonthlyPayment): Int {
        return monthlyPaymentRepository.update(monthlyPayment)
    }
}