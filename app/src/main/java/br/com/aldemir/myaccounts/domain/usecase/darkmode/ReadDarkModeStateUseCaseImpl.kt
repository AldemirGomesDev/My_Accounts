package br.com.aldemir.myaccounts.domain.usecase.darkmode

import br.com.aldemir.myaccounts.data.repository.darktheme.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadDarkModeStateUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ReadDarkModeStateUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readDarkModeState
    }
}