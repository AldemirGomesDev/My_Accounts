package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import javax.inject.Inject

class GetAllMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllMonthlyPaymentUseCase {
    override suspend fun invoke(): List<ExpenseMonthlyDTO> {
        return monthlyPaymentRepository.getAll()
    }
}