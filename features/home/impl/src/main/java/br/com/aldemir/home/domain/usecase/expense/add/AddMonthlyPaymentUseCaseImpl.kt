package br.com.aldemir.home.domain.usecase.expense.add

import br.com.aldemir.publ.domain.expense.AddMonthlyPaymentUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

class AddMonthlyPaymentUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): AddMonthlyPaymentUseCase {
    override suspend fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Long {
        return monthlyPaymentRepository.insertMonthlyPayment(expenseMonthlyDTO)
    }
}