package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class UpdateExpenseSituationUseCase(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
): UseCase<UpdateExpenseSituationUseCase.Params, Int> {

    override suspend fun execute(params: Params): Int {
        return monthlyPaymentRepository.updateSituationById(params.id, params.situation)
    }

    data class Params(
        val id: Int,
        val situation: Boolean
    )
}