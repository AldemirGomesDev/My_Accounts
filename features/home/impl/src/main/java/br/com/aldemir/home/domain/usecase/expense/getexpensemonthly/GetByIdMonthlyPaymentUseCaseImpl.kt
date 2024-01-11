package br.com.aldemir.home.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.publ.domain.expense.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository

class GetByIdMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetByIdMonthlyPaymentUseCase {
    override suspend fun invoke(id: Int): br.com.aldemir.data.database.model.ExpenseMonthlyDTO {
        return monthlyPaymentRepository.getByIdMonthlyPayment(id)
    }
}