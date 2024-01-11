package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

interface GetAllMonthlyPaymentUseCase {
    suspend operator fun invoke(): List<ExpenseMonthlyDTO>
}