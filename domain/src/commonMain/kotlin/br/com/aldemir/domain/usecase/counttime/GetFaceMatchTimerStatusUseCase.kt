package br.com.aldemir.domain.usecase.counttime

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.DataStoreRepository

class GetFaceMatchTimerStatusUseCase(
    private val dataStoreRepository: DataStoreRepository
) : UseCase<UseCase.None, GetFaceMatchTimerStatusUseCase.Result> {

    override suspend fun execute(params: UseCase.None): Result {
        val startTime = dataStoreRepository.getStartTime()
        val isFinished = dataStoreRepository.isCountTimeFinished()
        return Result(startTime, isFinished)
    }

    data class Result(
        val startTime: Long?,
        val isFinished: Boolean
    )
}