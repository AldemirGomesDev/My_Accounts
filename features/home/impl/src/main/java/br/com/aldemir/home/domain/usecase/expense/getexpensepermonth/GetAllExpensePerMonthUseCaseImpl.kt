package br.com.aldemir.home.domain.usecase.expense.getexpensepermonth

import br.com.aldemir.publ.domain.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository

class GetAllExpensePerMonthUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensePerMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<br.com.aldemir.data.database.model.ExpensePerMonthDTO> {
        return monthlyPaymentRepository.getAllExpensePerMonth(month, year)
    }
}