package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO

interface GetByIdMonthlyPaymentUseCase {
    suspend operator fun invoke(id: Int): ExpenseMonthlyDTO
}