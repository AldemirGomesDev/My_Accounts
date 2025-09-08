package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllMonthlyPaymentUseCase constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): BaseUseCase<Unit, List<ExpenseMonthlyDomain>> {

    override suspend fun execute(params: Unit): List<ExpenseMonthlyDomain> {
        return monthlyPaymentRepository.getAll()
    }
}