package br.com.aldemir.domain.usecase.expense

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.MonthlyPaymentRepository

class UpdateExpenseValueUseCase(
    private val repository: MonthlyPaymentRepository
): UseCase<UpdateExpenseValueUseCase.Params, Int> {
    override suspend fun execute(params: Params): Int {
        return repository.updateValue(params.id, params.value)
    }

    data class Params(
        val id: Int,
        val value: Double
    )
}