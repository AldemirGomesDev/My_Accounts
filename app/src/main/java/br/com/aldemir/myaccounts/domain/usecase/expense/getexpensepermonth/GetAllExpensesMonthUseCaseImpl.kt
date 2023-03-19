package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import javax.inject.Inject

class GetAllExpensesMonthUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensesMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<ExpenseMonthlyDTO> {
        return monthlyPaymentRepository.getAllExpensesMonth(month, year)
    }
}