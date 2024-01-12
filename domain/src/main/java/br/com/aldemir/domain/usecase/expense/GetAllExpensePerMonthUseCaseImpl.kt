package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllExpensePerMonthUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<ExpensePerMonthDomain> {
        return monthlyPaymentRepository.getAllExpensePerMonth(month, year)
    }
}