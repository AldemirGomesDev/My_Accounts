package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.common.util.DateUtils.getMonthByLanguage
import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.ExpenseMonthlyDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase.Params

class GetAllExpensesMonthUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<Params, List<ExpenseMonthlyDomain>> {
    override suspend fun execute(params: Params): List<ExpenseMonthlyDomain> {
        val expenses = monthlyPaymentRepository.getAllExpensesMonth(params.month, params.year)
        return expenses.map { expense ->
            expense.copy(month = getMonthByLanguage(expense.month))
        }
    }

    data class Params(
        val month: String,
        val year: String
    )
}
