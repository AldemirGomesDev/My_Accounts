package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.model.ExpenseMonthlyDomain

interface UpdateMonthlyPaymentUseCase {
    suspend operator fun invoke(expenseMonthlyDomain: ExpenseMonthlyDomain): Int
}