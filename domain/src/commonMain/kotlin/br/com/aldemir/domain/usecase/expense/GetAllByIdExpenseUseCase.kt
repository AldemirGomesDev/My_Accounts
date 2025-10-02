package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.common.util.DateUtils.getMonthByLanguage
import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.MonthlyPaymentDomain
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class GetAllByIdExpenseUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<Int, List<MonthlyPaymentDomain>> {

    override suspend fun execute(params: Int): List<MonthlyPaymentDomain> {
        val expenses = monthlyPaymentRepository.getAllByIdExpense(params)
        return expenses.map { expense ->
            expense.copy(month = getMonthByLanguage(expense.month))
        }
    }
}