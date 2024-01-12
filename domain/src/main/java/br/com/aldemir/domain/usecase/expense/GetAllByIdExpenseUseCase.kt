package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.MonthlyPaymentDomain

interface GetAllByIdExpenseUseCase {
    suspend operator fun invoke(id: Int): List<MonthlyPaymentDomain>
}