package br.com.aldemir.domain.usecase.counttime

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.DataStoreRepository

class SaveStartTimeUseCase(
    private val dataStoreRepository: DataStoreRepository
) : UseCase<Long, Unit> {

    override suspend fun execute(params: Long) {
        dataStoreRepository.saveStartTime(params)
    }
}