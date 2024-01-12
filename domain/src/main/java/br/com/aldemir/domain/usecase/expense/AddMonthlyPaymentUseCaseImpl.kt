package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.repository.MonthlyPaymentRepository
import br.com.aldemir.domain.model.ExpenseMonthlyDomain

class AddMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): AddMonthlyPaymentUseCase {
    override suspend fun invoke(expenseMonthlyDomain: ExpenseMonthlyDomain): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(expenseMonthlyDomain)
    }
}