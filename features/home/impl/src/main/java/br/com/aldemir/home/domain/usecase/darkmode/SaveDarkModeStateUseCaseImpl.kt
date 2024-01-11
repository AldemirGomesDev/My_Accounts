package br.com.aldemir.home.domain.usecase.darkmode

import br.com.aldemir.publ.domain.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.data.repository.darktheme.DataStoreRepository

class SaveDarkModeStateUseCaseImpl constructor(
    private val dataStoreRepository: DataStoreRepository
) : SaveDarkModeStateUseCase {
    override suspend fun invoke(isDarkMode: Boolean) {
        dataStoreRepository.saveDarkModeState(isDarkMode)
    }
}