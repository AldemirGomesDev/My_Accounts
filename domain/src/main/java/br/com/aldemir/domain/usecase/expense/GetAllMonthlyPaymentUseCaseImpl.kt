package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllMonthlyPaymentUseCase {
    override suspend fun invoke(): List<ExpenseMonthlyDomain> {
        return monthlyPaymentRepository.getAll()
    }
}