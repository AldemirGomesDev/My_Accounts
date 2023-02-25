package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain
import javax.inject.Inject

class GetAllByIdExpenseUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllByIdExpenseUseCase {
    override suspend fun invoke(id: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentRepository.getAllByIdExpense(id)
    }
}