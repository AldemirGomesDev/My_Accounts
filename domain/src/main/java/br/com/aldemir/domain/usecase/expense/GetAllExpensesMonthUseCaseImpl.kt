package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllExpensesMonthUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensesMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<ExpenseMonthlyDomain> {
        return monthlyPaymentRepository.getAllExpensesMonth(month, year)
    }
}