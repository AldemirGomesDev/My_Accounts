package br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly

import br.com.aldemir.myaccounts.domain.model.MonthlyPaymentDomain

interface GetAllByIdExpenseUseCase {
    suspend operator fun invoke(id: Int): List<MonthlyPaymentDomain>
}