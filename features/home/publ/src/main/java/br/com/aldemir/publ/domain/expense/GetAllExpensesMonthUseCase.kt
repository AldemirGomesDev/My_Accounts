package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

interface GetAllExpensesMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<ExpenseMonthlyDTO>
}