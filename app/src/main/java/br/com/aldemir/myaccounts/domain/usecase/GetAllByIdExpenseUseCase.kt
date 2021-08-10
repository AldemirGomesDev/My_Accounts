package br.com.aldemir.myaccounts.domain.usecase

import br.com.aldemir.myaccounts.domain.model.MonthlyPayment

interface GetAllByIdExpenseUseCase {
    suspend operator fun invoke(id: Int): List<MonthlyPayment>
}