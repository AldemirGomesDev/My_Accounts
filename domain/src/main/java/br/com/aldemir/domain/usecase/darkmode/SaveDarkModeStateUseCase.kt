package br.com.aldemir.domain.usecase.darkmode

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.DataStoreRepository

class SaveDarkModeStateUseCase constructor(
    private val dataStoreRepository: DataStoreRepository
) : BaseUseCase<Boolean, Unit> {

    override suspend fun execute(params: Boolean) {
        dataStoreRepository.saveDarkModeState(params)
    }
}