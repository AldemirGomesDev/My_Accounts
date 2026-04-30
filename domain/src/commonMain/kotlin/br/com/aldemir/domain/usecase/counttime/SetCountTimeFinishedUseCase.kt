package br.com.aldemir.domain.usecase.counttime

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.DataStoreRepository

class SetCountTimeFinishedUseCase(
    private val dataStoreRepository: DataStoreRepository
) : UseCase<UseCase.None, Unit> {
    override suspend fun execute(params: UseCase.None) {
        dataStoreRepository.setCountTimeFinished(true)
    }
}