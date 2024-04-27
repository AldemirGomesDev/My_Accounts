package br.com.aldemir.domain.usecase.darkmode

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.DataStoreRepository

class SaveDarkModeStateUseCase(
    private val dataStoreRepository: DataStoreRepository
) : BaseUseCase<String, Unit> {

    override suspend fun execute(params: String) {
        dataStoreRepository.saveDarkModeState(params)
    }
}