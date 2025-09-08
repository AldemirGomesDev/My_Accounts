package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class UpdateMonthlyPaymentUseCase constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): BaseUseCase<ExpenseMonthlyDomain, Int> {

    override suspend fun execute(params: ExpenseMonthlyDomain): Int {
        return monthlyPaymentRepository.update(params)
    }
}