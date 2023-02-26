package br.com.aldemir.myaccounts.domain.usecase.expense.add

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.expense.add.AddMonthlyPaymentUseCase
import javax.inject.Inject

class AddMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): AddMonthlyPaymentUseCase {
    override suspend fun invoke(monthlyPayment: MonthlyPayment): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(monthlyPayment)
    }
}