package br.com.aldemir.myaccounts.domain.usecase.darkmode

import br.com.aldemir.myaccounts.data.repository.DataStoreRepository
import javax.inject.Inject

class SaveDarkModeStateUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : SaveDarkModeStateUseCase {
    override suspend fun invoke(isDarkMode: Boolean) {
        dataStoreRepository.saveDarkModeState(isDarkMode)
    }
}