package br.com.aldemir.domain.usecase.darkmode


import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDarkModeStateUseCase (
    private val dataStoreRepository: DataStoreRepository
): BaseUseCase<Unit, Flow<String>> {

    override suspend fun execute(params: Unit): Flow<String> {
        return dataStoreRepository.readDarkModeState
    }
}