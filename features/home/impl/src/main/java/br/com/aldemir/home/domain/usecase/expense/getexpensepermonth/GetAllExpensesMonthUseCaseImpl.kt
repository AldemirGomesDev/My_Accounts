package br.com.aldemir.home.domain.usecase.expense.getexpensepermonth

import br.com.aldemir.publ.domain.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository

class GetAllExpensesMonthUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllExpensesMonthUseCase {
    override suspend fun invoke(month: String, year: String): List<br.com.aldemir.data.database.model.ExpenseMonthlyDTO> {
        return monthlyPaymentRepository.getAllExpensesMonth(month, year)
    }
}