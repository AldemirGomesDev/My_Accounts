package br.com.aldemir.home.domain.usecase.darkmode

import br.com.aldemir.publ.domain.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.data.repository.darktheme.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDarkModeStateUseCaseImpl constructor(
    private val dataStoreRepository: DataStoreRepository
): ReadDarkModeStateUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readDarkModeState
    }
}