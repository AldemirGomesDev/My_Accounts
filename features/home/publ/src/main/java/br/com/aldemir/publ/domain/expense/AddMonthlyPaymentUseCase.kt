package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

interface AddMonthlyPaymentUseCase {
    suspend operator fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Long
}