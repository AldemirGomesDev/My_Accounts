package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetByIdMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetByIdMonthlyPaymentUseCase {
    override suspend fun invoke(id: Int): ExpenseMonthlyDomain {
        return monthlyPaymentRepository.getByIdMonthlyPayment(id)
    }
}