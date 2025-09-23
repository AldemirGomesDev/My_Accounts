package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllByIdExpenseUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<Int, List<MonthlyPaymentDomain>> {

    override suspend fun execute(params: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentRepository.getAllByIdExpense(params)
    }
}