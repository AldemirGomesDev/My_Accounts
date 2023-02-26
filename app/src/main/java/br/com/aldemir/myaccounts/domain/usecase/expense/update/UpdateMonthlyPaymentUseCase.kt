package br.com.aldemir.myaccounts.domain.usecase.expense.update

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface UpdateMonthlyPaymentUseCase {
    suspend operator fun invoke(monthlyPayment: MonthlyPayment): Int
}