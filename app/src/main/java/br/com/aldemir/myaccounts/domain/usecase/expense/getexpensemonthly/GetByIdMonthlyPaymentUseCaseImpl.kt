package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly.GetByIdMonthlyPaymentUseCase
import javax.inject.Inject

class GetByIdMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetByIdMonthlyPaymentUseCase {
    override suspend fun invoke(id: Int): MonthlyPayment {
        return monthlyPaymentRepository.getByIdMonthlyPayment(id)
    }
}