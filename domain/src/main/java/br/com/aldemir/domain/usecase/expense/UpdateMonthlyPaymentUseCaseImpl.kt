package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class UpdateMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UpdateMonthlyPaymentUseCase {
    override suspend fun invoke(expenseMonthlyDomain: ExpenseMonthlyDomain): Int {
        return monthlyPaymentRepository.update(expenseMonthlyDomain)
    }
}