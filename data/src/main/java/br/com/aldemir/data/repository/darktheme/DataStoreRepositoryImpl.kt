package br.com.aldemir.data.repository.darktheme

import br.com.aldemir.data.database.preference.DataStorePreference
import br.com.aldemir.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreRepositoryImpl(
    private val dataStorePreference: DataStorePreference
) : DataStoreRepository {
    override suspend fun saveDarkModeState(isDarkMode: String) {
        dataStorePreference.saveDarkModeState(isDarkMode)
    }

    override val readDarkModeState: Flow<String>
        get() = dataStorePreference.readDarkModeState
}