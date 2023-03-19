package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO

interface GetAllMonthlyPaymentUseCase {
    suspend operator fun invoke(): List<ExpenseMonthlyDTO>
}