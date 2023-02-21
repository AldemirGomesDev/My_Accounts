package br.com.aldemir.myaccounts.data.repository

import br.com.aldemir.myaccounts.data.database.preference.DataStorePreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
) : DataStoreRepository {
    override suspend fun saveDarkModeState(isDarkMode: Boolean) {
        dataStorePreference.saveDarkModeState(isDarkMode)
    }

    override val readDarkModeState: Flow<Boolean>
        get() = dataStorePreference.readDarkModeState
}