package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain

interface GetByIdMonthlyPaymentUseCase {
    suspend operator fun invoke(id: Int): ExpenseMonthlyDomain
}