package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface GetAllExpensesMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<MonthlyPayment>
}