package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpensePerMonthDTO


interface GetAllExpensePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpensePerMonthDTO>
}