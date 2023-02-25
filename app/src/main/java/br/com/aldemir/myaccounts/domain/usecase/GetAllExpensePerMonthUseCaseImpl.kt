package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpensePerMonth
import javax.inject.Inject

class GetAllExpensePerMonthUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<ExpensePerMonth> {
        return monthlyPaymentRepository.getAllExpensePerMonth(month, year)
    }
}