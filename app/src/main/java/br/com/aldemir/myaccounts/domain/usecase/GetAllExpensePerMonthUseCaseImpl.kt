package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.ExpensePerMonth
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import javax.inject.Inject

class GetAllExpensePerMonthUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<ExpensePerMonth> {
        return monthlyPaymentRepository.getAllExpensePerMonth(month, year)
    }
}