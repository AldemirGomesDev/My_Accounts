package br.com.aldemir.home.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.publ.domain.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository

class GetAllByIdExpenseUseCaseImpl constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): GetAllByIdExpenseUseCase {
    override suspend fun invoke(id: Int): List<br.com.aldemir.data.database.model.MonthlyPaymentDomain> {
        return monthlyPaymentRepository.getAllByIdExpense(id)
    }
}