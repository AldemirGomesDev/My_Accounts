package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

interface UpdateMonthlyPaymentUseCase {
    suspend operator fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Int
}