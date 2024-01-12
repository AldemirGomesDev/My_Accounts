package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllByIdExpenseUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllByIdExpenseUseCase {
    override suspend fun invoke(id: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentRepository.getAllByIdExpense(id)
    }
}