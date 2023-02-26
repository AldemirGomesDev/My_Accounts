package br.com.aldemir.myaccounts.domain.usecase.expense.add

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface AddMonthlyPaymentUseCase {
    suspend operator fun invoke(monthlyPayment: MonthlyPayment): Long
}