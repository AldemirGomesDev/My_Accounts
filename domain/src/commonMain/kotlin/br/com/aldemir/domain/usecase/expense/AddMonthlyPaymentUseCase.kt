package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.MonthlyPaymentRepository
import br.com.aldemir.domain.model.ExpenseMonthlyDomain

class AddMonthlyPaymentUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<ExpenseMonthlyDomain, Long> {

    override suspend fun execute(params: ExpenseMonthlyDomain): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(params)
    }
}