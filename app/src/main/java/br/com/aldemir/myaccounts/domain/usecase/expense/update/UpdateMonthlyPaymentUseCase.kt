package br.com.aldemir.myaccounts.domain.usecase.expense.update

import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO

interface UpdateMonthlyPaymentUseCase {
    suspend operator fun invoke(expenseMonthlyDTO: ExpenseMonthlyDTO): Int
}