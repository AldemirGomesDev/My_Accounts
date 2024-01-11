package br.com.aldemir.home.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.publ.domain.expense.GetAllMonthlyPaymentUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository

class GetAllMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllMonthlyPaymentUseCase {
    override suspend fun invoke(): List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO> {
        return monthlyPaymentRepository.getAll()
    }
}