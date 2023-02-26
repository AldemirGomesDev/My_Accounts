package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.model.MonthlyPayment

interface GetByIdMonthlyPaymentUseCase {
    suspend operator fun invoke(id: Int): MonthlyPayment
}