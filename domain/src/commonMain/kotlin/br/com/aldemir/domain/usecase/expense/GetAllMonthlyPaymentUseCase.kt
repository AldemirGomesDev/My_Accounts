package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllMonthlyPaymentUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<Unit, List<ExpenseMonthlyDomain>> {

    override suspend fun execute(params: Unit): List<ExpenseMonthlyDomain> {
        return monthlyPaymentRepository.getAll()
    }
}