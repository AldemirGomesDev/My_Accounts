package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpensePerMonthDTO
import javax.inject.Inject

class GetAllExpensePerMonthUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<ExpensePerMonthDTO> {
        return monthlyPaymentRepository.getAllExpensePerMonth(month, year)
    }
}