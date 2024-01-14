package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetByIdMonthlyPaymentUseCase constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): BaseUseCase<Int, ExpenseMonthlyDomain> {

    override suspend fun execute(params: Int): ExpenseMonthlyDomain {
        return monthlyPaymentRepository.getByIdMonthlyPayment(params)
    }
}