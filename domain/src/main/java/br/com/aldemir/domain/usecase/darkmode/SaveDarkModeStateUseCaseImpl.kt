package br.com.aldemir.domain.usecase.darkmode

import br.com.aldemir.domain.repository.DataStoreRepository

class SaveDarkModeStateUseCaseImpl constructor(
    private val dataStoreRepository: DataStoreRepository
) : SaveDarkModeStateUseCase {
    override suspend fun invoke(isDarkMode: Boolean) {
        dataStoreRepository.saveDarkModeState(isDarkMode)
    }
}