package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import javax.inject.Inject

class GetAllMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllMonthlyPaymentUseCase {
    override suspend fun invoke(): List<MonthlyPayment> {
        return monthlyPaymentRepository.getAll()
    }
}