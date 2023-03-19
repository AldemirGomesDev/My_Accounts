package br.com.aldemir.myaccounts.domain.usecase.expense.add

import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO

interface AddMonthlyPaymentUseCase {
    suspend operator fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Long
}