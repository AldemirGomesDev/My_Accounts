package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetByIdMonthlyPaymentUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<Int, ExpenseMonthlyDomain> {

    override suspend fun execute(params: Int): ExpenseMonthlyDomain {
        return monthlyPaymentRepository.getByIdMonthlyPayment(params)
    }
}