package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.MonthlyPaymentRepository
import br.com.aldemir.domain.model.ExpenseMonthlyDomain

class AddMonthlyPaymentUseCase constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): BaseUseCase<ExpenseMonthlyDomain, Long> {

    override suspend fun execute(params: ExpenseMonthlyDomain): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(params)
    }
}