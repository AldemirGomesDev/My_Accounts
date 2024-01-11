package br.com.aldemir.home.domain.usecase.expense.update

import br.com.aldemir.publ.domain.expense.UpdateMonthlyPaymentUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository

class UpdateMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UpdateMonthlyPaymentUseCase {
    override suspend fun invoke(expenseMonthlyDTO: br.com.aldemir.data.database.model.ExpenseMonthlyDTO): Int {
        return monthlyPaymentRepository.update(expenseMonthlyDTO)
    }
}