package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly.GetAllMonthlyPaymentUseCase
import javax.inject.Inject

class GetAllMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllMonthlyPaymentUseCase {
    override suspend fun invoke(): List<MonthlyPayment> {
        return monthlyPaymentRepository.getAll()
    }
}