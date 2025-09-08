package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase.Params

class GetAllExpensesMonthUseCase constructor(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): BaseUseCase<Params, List<ExpenseMonthlyDomain>> {
    override suspend fun execute(params: Params): List<ExpenseMonthlyDomain> {
        return monthlyPaymentRepository.getAllExpensesMonth(params.month, params.year)
    }

    data class Params(
        val month: String,
        val year: String
    )
}
