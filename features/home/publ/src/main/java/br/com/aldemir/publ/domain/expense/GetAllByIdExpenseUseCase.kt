package br.com.aldemir.publ.domain.expense

import br.com.aldemir.data.database.model.MonthlyPaymentDomain

interface GetAllByIdExpenseUseCase {
    suspend operator fun invoke(id: Int): List<MonthlyPaymentDomain>
}