package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.ExpensePerMonthDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllExpensePerMonthUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<GetAllExpensePerMonthUseCase.Params, List<ExpensePerMonthDomain>> {

    override suspend fun execute(params: Params): List<ExpensePerMonthDomain> {
        return monthlyPaymentRepository.getAllExpensePerMonth(params.month, params.year)
    }

    data class Params(
        val month: String,
        val year: String
    )
}