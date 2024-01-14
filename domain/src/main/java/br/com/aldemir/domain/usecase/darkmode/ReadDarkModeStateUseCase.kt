package br.com.aldemir.domain.usecase.darkmode


import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadDarkModeStateUseCase constructor(
    private val dataStoreRepository: DataStoreRepository
): BaseUseCase<Unit, Flow<Boolean>> {

    override suspend fun execute(params: Unit): Flow<Boolean> {
        return dataStoreRepository.readDarkModeState
    }
}