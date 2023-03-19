package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth

import br.com.aldemir.myaccounts.data.model.ExpensePerMonthDTO


interface GetAllExpensePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpensePerMonthDTO>
}