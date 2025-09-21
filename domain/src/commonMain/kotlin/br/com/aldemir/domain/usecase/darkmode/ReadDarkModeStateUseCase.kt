package br.com.aldemir.domain.usecase.darkmode

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDarkModeStateUseCase (
    private val dataStoreRepository: DataStoreRepository
): UseCase<Unit, Flow<String>> {

    override suspend fun execute(params: Unit): Flow<String> {
        return dataStoreRepository.readDarkModeState
    }
}