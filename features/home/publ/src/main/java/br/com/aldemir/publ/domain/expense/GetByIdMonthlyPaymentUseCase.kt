package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.ExpenseMonthlyDTO

interface GetByIdMonthlyPaymentUseCase {
    suspend operator fun invoke(id: Int): ExpenseMonthlyDTO
}