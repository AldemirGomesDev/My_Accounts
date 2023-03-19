package br.com.aldemir.myaccounts.domain.usecase.expense.add

import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import javax.inject.Inject

class AddMonthlyPaymentUseCaseImpl @Inject constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): AddMonthlyPaymentUseCase {
    override suspend fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(expenseMonthlyDTO)
    }
}