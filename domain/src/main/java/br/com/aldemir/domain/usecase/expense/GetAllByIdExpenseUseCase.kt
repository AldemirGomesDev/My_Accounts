package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllByIdExpenseUseCase constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): BaseUseCase<Int, List<MonthlyPaymentDomain>> {

    override suspend fun execute(params: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentRepository.getAllByIdExpense(params)
    }
}