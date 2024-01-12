package br.com.aldemir.domain.usecase.darkmode


import br.com.aldemir.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDarkModeStateUseCaseImpl constructor(
    private val dataStoreRepository: DataStoreRepository
): ReadDarkModeStateUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readDarkModeState
    }
}