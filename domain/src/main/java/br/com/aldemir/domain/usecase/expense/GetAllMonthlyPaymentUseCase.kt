package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain

interface GetAllMonthlyPaymentUseCase {
    suspend operator fun invoke(): List<ExpenseMonthlyDomain>
}