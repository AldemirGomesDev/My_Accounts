package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import javax.inject.Inject

class GetByIdMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetByIdMonthlyPaymentUseCase {
    override suspend fun invoke(id: Int): ExpenseMonthlyDTO {
        return monthlyPaymentRepository.getByIdMonthlyPayment(id)
    }
}