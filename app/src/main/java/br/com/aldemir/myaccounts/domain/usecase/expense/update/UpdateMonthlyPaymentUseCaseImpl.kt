package br.com.aldemir.myaccounts.domain.usecase.expense.update

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import javax.inject.Inject

class UpdateMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UpdateMonthlyPaymentUseCase {
    override suspend fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Int {
        return monthlyPaymentRepository.update(expenseMonthlyDTO)
    }
}