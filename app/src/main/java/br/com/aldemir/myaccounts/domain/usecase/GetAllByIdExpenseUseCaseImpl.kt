package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import javax.inject.Inject

class GetAllByIdExpenseUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllByIdExpenseUseCase {
    override suspend fun invoke(id: Int): List<MonthlyPayment> {
        return monthlyPaymentRepository.getAllByIdExpense(id)
    }
}