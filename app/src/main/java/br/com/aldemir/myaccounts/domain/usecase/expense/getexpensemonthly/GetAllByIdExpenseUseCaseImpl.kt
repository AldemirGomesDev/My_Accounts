package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly.GetAllByIdExpenseUseCase
import javax.inject.Inject

class GetAllByIdExpenseUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllByIdExpenseUseCase {
    override suspend fun invoke(id: Int): List<MonthlyPaymentDomain> {
        return monthlyPaymentRepository.getAllByIdExpense(id)
    }
}