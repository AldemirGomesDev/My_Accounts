package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import javax.inject.Inject

class GetAllExpensesMonthUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensesMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<MonthlyPayment> {
        return monthlyPaymentRepository.getAllExpensesMonth(month, year)
    }
}